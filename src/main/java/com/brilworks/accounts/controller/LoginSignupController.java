package com.brilworks.accounts.controller;


import com.brilworks.accounts.auth.BrilEmpMS;
import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import com.brilworks.accounts.dto.*;
import com.brilworks.accounts.entity.Organisation;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.exception.AuthorizationException;
import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.entity.UserRole;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import com.brilworks.accounts.role_permissions.services.RolePermissionMappingService;
import com.brilworks.accounts.role_permissions.services.RoleService;
import com.brilworks.accounts.role_permissions.services.UserRoleService;
import com.brilworks.accounts.services.AuthService;
import com.brilworks.accounts.services.OrganisationService;
import com.brilworks.accounts.services.UserService;
import com.brilworks.accounts.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/rest/accounts")
@RestController
public class LoginSignupController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStoreService tokenStoreService;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private AuthValidator authValidator;
    @Autowired
    private BrilEmpMS brilEmpMS;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @Autowired
    private RolePermissionMappingService rolePermissionMappingService;

    @PostMapping(value = "/logIn")
    public LoginResponse logIn(@RequestBody LoginRequest logInRequest) {
        try {
            AccessTokenModel accessTokenModel = this.userService.getUserDetailForLogin(logInRequest);
            LoginResponse value = tokenStoreService.generateAccessToken(accessTokenModel);
            userService.saveUserToken(value.getUserId(), value.getToken());
            return value;
        } catch (AuthorizationException e) {
            if (Constants.PASSWORD_NOT_MATCH.equals(e.getErrorMessage())) {
                this.tokenStoreService.incrementInvalidPasswordCount(logInRequest.getEmail());
                if (this.tokenStoreService.checkAccountLocked(logInRequest.getEmail())) {
                    throw new AuthorizationException(Constants.MAX_LIMIT_REACHED, Constants.MAX_LIMIT_REACHED);
                }
            }
            throw e;
        }
    }

    @Transactional
    @PostMapping(value = "/signUp")
    public LoginResponse signUp(@RequestBody LoginRequest LoginRequest) {
        userService.signUp(LoginRequest);
        AccessTokenModel accessTokenModel = this.userService.getUserDetailForSignup(LoginRequest);
        LoginResponse response = tokenStoreService.generateAccessToken(accessTokenModel);
        return response;
    }

    @GetMapping("/accessTokenModel")
    public AccessTokenModel getAccessTokenModelByModuleType(HttpServletRequest httpRequest) {
        String authToken = httpRequest.getHeader("Authorization");
        AccessTokenModel accessToken = tokenStoreService.readAccessToken(authToken);

        List<RoleDto> roles = rolePermissionMappingService.getRoleIdByUserIdAndOrgId(accessToken.getUserId(), null != accessToken.getOrgId() ? accessToken.getOrgId() : accessToken.getOrganizationId() );
        List<Long> roleIdList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (RoleDto role : roles) {
                roleIdList.add(role.getRoleId());
            }
        }
        List<PermissionsEnum> list = rolePermissionMappingService.getPermissionList(roleIdList);
        accessToken.setOrgId(null != accessToken.getOrgId() ? accessToken.getOrgId() : accessToken.getOrganizationId());
        accessToken.setPermissionList(list);
        return accessToken;
    }

    @GetMapping("/org")
    public List<OrganisationDto> getListOfOrganisations(Authentication auth) {
        User user = authValidator.authUser(auth);
        return organisationService.getListOfOrganisations(user.getId());
    }

    @GetMapping("/getToken/{orgId}/{userId}")
    public String getTokenForSignUpUser(@PathVariable("userId") Long userId, @PathVariable("userId") Long orgId) {
        User user = userService.getUserById(userId);
        AccessTokenModel accessTokenModel = new AccessTokenModel(user.getEmail(), user.getPassword(), user, orgId, null);
        LoginResponse response = tokenStoreService.generateAccessToken(accessTokenModel);
        return response.getToken();
    }
}
