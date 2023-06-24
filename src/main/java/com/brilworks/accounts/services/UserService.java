package com.brilworks.accounts.services;

import com.brilworks.accounts.auth.emp.EmployeeOrgBasic;
import com.brilworks.accounts.auth.emp.InviteDto;
import com.brilworks.accounts.dto.*;
import com.brilworks.accounts.entity.Organisation;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    AccessTokenModel getUserDetailForSignup(LoginRequest signUpRequest);

    UserDto findById(Long id);

    void saveUserData(UserDto employeeDto);

    User getUserById(Long empId);


    User getEmployeeByEmailOrUserName(String email);

    AccessTokenModel getUserDetailForLogin(LoginRequest loginDto);

    EmployeeOrgBasic inviteUser(InviteDto inviteDto, Long orgId, ModuleType moduleType);

    User signUp(LoginRequest logInRequest);

    User findByEmail(String email);

    List<User> getUsers();

    void saveUserToken(Long userId, String token);

    void createUser(InviteDto inviteDto, Long organizationId);

    void changePassword(PasswordChangeRequest passwordChangeRequest);

    void generateForgetPasswordLink(String email);

    User getUserByToken(String token);

    void enableOrDisableUser(Boolean isSignUp, Long empId);

    void sendInviteLinkAgain(Long empId, Long organizationId, Long userId);

    void setPasswordSignUpTime(Long brilUsetrId, SetPasswordRequest setPassword);
}
