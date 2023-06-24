package com.brilworks.accounts.services;

import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import com.brilworks.accounts.dto.AccessTokenModel;
import com.brilworks.accounts.exception.AuthorizationException;
import com.brilworks.accounts.role_permissions.dto.AuthDetails;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import com.brilworks.accounts.role_permissions.services.RolePermissionMappingService;
import com.brilworks.accounts.role_permissions.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private TokenStoreService tokenStoreService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionMappingService rolePermissionMappingService;

    public AccessTokenModel getAuthTokenModel(HttpServletRequest httpRequest) {
        String authToken = httpRequest.getHeader("Authorization");
        return tokenStoreService.readAccessToken(authToken);
    }

    public AuthDetails getAuthDetails(HttpServletRequest httpRequest) {
        return new AuthDetails(getAuthTokenModel(httpRequest));
    }

    public List<PermissionsEnum> getLoggedInUserPermissions(HttpServletRequest httpRequest) {
        AuthDetails authDetails = getAuthDetails(httpRequest);
        List<Long> roleIds = userRoleService.getUserRoldIdsInOrg(authDetails.getUserId(), authDetails.getOrganizationId());
        return rolePermissionMappingService.getPermissionList(roleIds);
    }

    public List<PermissionsEnum> getUserPermission(Long userId, HttpServletRequest httpRequest) {
        AuthDetails authDetails = getAuthDetails(httpRequest);
        List<Long> roleIds = userRoleService.getUserRoleIds(userId, authDetails.getOrganizationId());
        return rolePermissionMappingService.getPermissionList(roleIds);
    }

    public void isAdmin(HttpServletRequest httpRequest, ModuleType moduleType) {
        List<PermissionsEnum> permissionsEnums = getLoggedInUserPermissions(httpRequest);
        PermissionsEnum adminPermission;
        switch (moduleType) {
            case EMP:
                adminPermission = PermissionsEnum.ALL_EMP;
                break;
            case OKR:
                adminPermission = PermissionsEnum.ALL_OKR;
                break;
            case TASK:
                adminPermission = PermissionsEnum.ALL_TASK;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + moduleType);
        }
        boolean admin = permissionsEnums.contains(adminPermission);
        if(!admin){
            throw new AuthorizationException();
        }
    }

}
