package com.brilworks.accounts.role_permissions.controller;

import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.services.AuthService;
import com.brilworks.accounts.utils.Constants;
import com.brilworks.accounts.role_permissions.dto.AuthDetails;
import com.brilworks.accounts.role_permissions.dto.ResponseDto;
import com.brilworks.accounts.role_permissions.dto.RoleWithListOfPermissionDto;
import com.brilworks.accounts.role_permissions.services.RolePermissionMappingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SecurityRequirement(name = "brilworks")
@RequestMapping("/rest/accounts/role-permission/{organisationUrl}")
@RestController
public class RolePermissionController {
    @Autowired
    private RolePermissionMappingService rolePermissionMappingService;
    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseDto createRolePermissionMapping(@RequestBody RoleWithListOfPermissionDto rolePermissionDto,
                                                   HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        rolePermissionMappingService.saveListOfPermissionWithRoleId(rolePermissionDto, authDetails.getOrganizationId());
        return new ResponseDto(Constants.SUCCESS, Constants.SAVED);
    }

    @GetMapping("/getMappedPermissions/module/{type}")
    public List<String> findPermissionForEmployee(@PathVariable("organisationUrl") String organisationUrl,
                                                  @PathVariable("type") String moduleType,
                                                  HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        return rolePermissionMappingService.getPermissionListInArray(authDetails.getUserId(), authDetails.getOrganizationId(), moduleType);
    }

    @GetMapping("/module/{type}")
    public List<Permission> getAllPermissionsOfModule(@PathVariable("organisationUrl") String organisationUrl,
                                                      @PathVariable("type") String moduleType,
                                                      HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        return rolePermissionMappingService.getAllPermissionsOfModule(authDetails.getOrganizationId(), ModuleType.valueOf(moduleType));
    }

    @GetMapping("/{roleId}")
    public RoleWithListOfPermissionDto getRoleAndPermissionListByRoleId(@PathVariable("roleId") Long roleId,
                                                                        HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        return rolePermissionMappingService.getRoleAndPermissionListByRoleId(roleId, authDetails.getOrganizationId());
    }

    @PutMapping
    public ResponseDto updateRolePermissionMapping(@RequestBody RoleWithListOfPermissionDto rolePermissionDto,
                                                   HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        rolePermissionMappingService.updateRolePermissionMapping(rolePermissionDto,authDetails.getOrganizationId());
        return new ResponseDto(Constants.SUCCESS, Constants.UPDATED);
    }

    @DeleteMapping("/{roleId}")
    public ResponseDto deleteRole(@PathVariable("organisationUrl") String organisationUrl,
                                  @PathVariable("roleId") Long roleId,
                                  HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        rolePermissionMappingService.deleteRolePermissionsOfRole(roleId);
        return new ResponseDto(Constants.SUCCESS, Constants.DELETED);
    }
}


