package com.brilworks.accounts.services.impl;


import com.brilworks.accounts.auth.BrilEmpMS;
import com.brilworks.accounts.auth.emp.EmployeeOrgBasic;
import com.brilworks.accounts.auth.emp.InviteDto;
import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import com.brilworks.accounts.dto.*;
import com.brilworks.accounts.entity.Organisation;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.exception.AuthorizationException;
import com.brilworks.accounts.exception.ConflictException;
import com.brilworks.accounts.exception.NotFoundException;
import com.brilworks.accounts.repository.UserRepository;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.repository.PermissionRepository;
import com.brilworks.accounts.role_permissions.services.RoleService;
import com.brilworks.accounts.role_permissions.services.UserRoleService;
import com.brilworks.accounts.services.OrganisationService;
import com.brilworks.accounts.services.UserService;
import com.brilworks.accounts.utils.Constants;
import com.brilworks.accounts.utils.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TokenStoreService tokenService;
    @Autowired
    private BrilEmpMS brilEmpMS;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MyEmailService myEmailService;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public AccessTokenModel getUserDetailForSignup(LoginRequest loginDto) {
        User user = getEmployeeByEmailOrUserName(loginDto.getEmail());
        if (null == user) {
            throw new NotFoundException(NotFoundException.NotFound.EMAIL_NOT_FOUND);
        }
        if (this.passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return new AccessTokenModel(loginDto.getEmail(), loginDto.getPassword(), user, null, null);

        } else {
            throw new AuthorizationException(Constants.PASSWORD_NOT_MATCH, Constants.PASSWORD_NOT_MATCH);
        }
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> emp = userRepository.findById(id);
        return emp.map(employee -> new UserDto().entityToDto(employee)).orElse(null);
    }

    @Override
    public void saveUserData(UserDto employeeDto) {
        userRepository.save(employeeDto.dtoToEntity(employeeDto));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(NotFoundException.NotFound.USER_NOT_FOUND));
    }

    @Override
    public User getEmployeeByEmailOrUserName(String email) {
        return userRepository.getEmployeeByEmailOrUserName(email, email);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUserToken(Long userId, String token) {
        User user = getUser(userId);
        user.setToken(token);
        userRepository.save(user);
    }

    @Override
    public void createUser(InviteDto inviteDto, Long organizationId) {
        checkUserAndIfNotExistsCreateUser(inviteDto);

    }

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        if (passwordChangeRequest.getUserId() == null) {
            throw new NotFoundException(NotFoundException.NotFound.USER_ID_MISSING);
        }
        Optional<User> user = userRepository.findById(passwordChangeRequest.getUserId());
        if (user.isEmpty()) {
            throw new NotFoundException(NotFoundException.NotFound.USER_NOT_FOUND);
        }
        if (!passwordChangeRequest.getPassword().equals(passwordChangeRequest.getConfirmPassword())) {
            throw new NotFoundException(NotFoundException.NotFound.PASSWORD_AND_CONFIRM_PASS_NOT_MATCH);
        }
        String password = passwordEncoder.encode(passwordChangeRequest.getPassword());
        user.get().setPassword(password);
        userRepository.save(user.get());
    }

    @Override
    public void generateForgetPasswordLink(String email) {
        User user = userService.findByEmail(email);
        if (null == user) {
            throw new NotFoundException(NotFoundException.NotFound.EMAIL_NOT_FOUND);
        }
        ForgetPassEmailData forgetPassEmailData = new ForgetPassEmailData();
        forgetPassEmailData.setEmail(email);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), "", new ArrayList<>());
        String token = jwtUtil.generateToken(userDetails);
        user.setToken(token);
        userRepository.save(user);
        forgetPassEmailData.setToken(token);
        myEmailService.genaRateForgetPasswordLink(forgetPassEmailData);
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.getUserByToken(token);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(NotFoundException.NotFound.USER_NOT_FOUND));
    }

    @Override
    public AccessTokenModel getUserDetailForLogin(LoginRequest loginDto) {
        User user = getEmployeeByEmailOrUserName(loginDto.getEmail());
        if (null == user) {
            throw new NotFoundException(NotFoundException.NotFound.EMAIL_NOT_FOUND);
        }
        if (this.passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return new AccessTokenModel(loginDto.getEmail(), loginDto.getPassword(), user, null, null);

        } else {
            throw new AuthorizationException(Constants.PASSWORD_NOT_MATCH, Constants.PASSWORD_NOT_MATCH);
        }
    }

    @Override
    @Transactional
    public EmployeeOrgBasic inviteUser(InviteDto inviteDto, Long orgId, ModuleType moduleType) {
        User userById = userRepository.findByEmail(inviteDto.getEmail());
        if (userById == null) {
            userById = createUserRecord(inviteDto);
        }

        userRoleService.assignRoleToUser(userById.getId(), inviteDto.getRoleId(), orgId);
        // send email for invite

        EmployeeOrgBasic employeeOrgBasic = new EmployeeOrgBasic(inviteDto);
        employeeOrgBasic.setRoleId(inviteDto.getRoleId());
        employeeOrgBasic.setBrilUserId(userById.getId());
        return employeeOrgBasic;
    }

    private User createUserRecord(InviteDto inviteDto) {
        User user = new User();
        user.setFirstName(inviteDto.getFirstName());
        user.setLastName(inviteDto.getLastName());
        user.setEmail(inviteDto.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User signUp(LoginRequest logInRequest) {
        if (StringUtils.isBlank(logInRequest.getEmail())) {
            throw new ConflictException(ConflictException.UserExceptionConflictMsg.EMPTY_EMAIL);
        }
        if (StringUtils.isBlank(logInRequest.getPassword())) {
            throw new ConflictException(ConflictException.UserExceptionConflictMsg.EMPTY_PASSWORD);
        }
        //check emp already connect with requested org or not
         User user = checkUserAndIfNotExistsCreateUser(logInRequest);
         return user;
    }

    private User checkUserAndIfNotExistsCreateUser(LoginRequest logInRequest) {
        User userById = userRepository.findByEmail(logInRequest.getEmail());
        if (userById != null) {
            return userById;
        }else {
            User user = new User();
            user.setEmail(logInRequest.getEmail());
            user.setPassword(this.passwordEncoder.encode(logInRequest.getPassword()));
            user = userRepository.save(user);
            return user;
        }
    }

    private void checkUserAndIfNotExistsCreateUser(InviteDto logInRequest) {
        User userById = userRepository.findByEmail(logInRequest.getEmail());
        if (userById != null) {
            throw new NotFoundException(NotFoundException.NotFound.EMAIL_ALREADY_PRESENT_FOUND);
        }
        User user = new User();
        user.setEmail(logInRequest.getEmail());
        user.setFirstName(logInRequest.getFirstName());
        user.setLastName(logInRequest.getLastName());
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void enableOrDisableUser(Boolean signUpOrNot, Long empId) {
        User user = userService.getUserById(empId);
        user.setIsSignUp(signUpOrNot);
        userRepository.save(user);
    }

    @Override
    public void sendInviteLinkAgain(Long empId, Long organizationId, Long userId) {
        OrganisationDto orgById = organisationService.findById(organizationId);
        EmailData emailData = new EmailData();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(NotFoundException.NotFound.EMPLOYEE_NOT_FOUND);
        }
        emailData.setEmpId(empId);
        emailData.setEmailTo(user.get().getEmail());
        emailData.setOrganisationUrl(orgById.getOrganisationUrl());
        emailData.setOrgId(organizationId);
    }

    @Override
    public void setPasswordSignUpTime(Long brilUsetrId, SetPasswordRequest setPassword) {
        Optional<User> user = userRepository.findById(brilUsetrId);
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(setPassword.getPassword()));
            user.get().setIsSignUp(true);
            userRepository.save(user.get());
        }
    }
}
