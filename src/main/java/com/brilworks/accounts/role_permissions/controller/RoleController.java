package com.brilworks.accounts.role_permissions.controller;

import com.brilworks.accounts.role_permissions.dto.AuthDetails;
import com.brilworks.accounts.role_permissions.dto.ResponseDto;
import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.services.RoleService;
import com.brilworks.accounts.services.AuthService;
import com.brilworks.accounts.utils.Constants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@SecurityRequirement(name = "brilworks")
@RequestMapping("/rest/accounts/{organisationUrl}/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @PostMapping("/test")
    public Long createRole1(@RequestBody @Validated RoleDto roleDto, HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        roleDto.setOrganisationId(authDetails.getOrganizationId());
        return roleService.createRole(roleDto, authDetails.getOrganizationId());
    }

    @PostMapping
    public Long createRole(@RequestBody @Validated RoleDto roleDto, HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        roleDto.setOrganisationId(authDetails.getOrganizationId());
        return roleService.createRole(roleDto, authDetails.getOrganizationId());
    }

    @PutMapping
    public ResponseDto updateRole(@RequestBody @Validated RoleDto roleDto, HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        roleDto.setOrganisationId(authDetails.getOrganizationId());
        roleService.updateRole(roleDto);
        return new ResponseDto(Constants.SUCCESS, Constants.UPDATED);
    }

    @GetMapping("/getAll/module/{type}")
    public List<RoleDto> getAllRole(@PathVariable("organisationUrl") String organisationUrl,
                                    @PathVariable("type") String moduleType,
                                    HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        return roleService.getRoles(authDetails.getOrganizationId(), moduleType);
    }

    @GetMapping("/{id}")
    public RoleDto getRoleById(@PathVariable("id") Long id, HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        return roleService.findRoleDtoById(id, authDetails.getOrganizationId());
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteRole(@PathVariable("id") Long roleId, HttpServletRequest httpRequest) {
        authService.getAuthDetails(httpRequest);
        roleService.deleteRole(roleId);
        return new ResponseDto(Constants.SUCCESS, Constants.DELETED);
    }

    @GetMapping("/permissions")
    public List<Permission> getAllPermissionByToken(HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        return roleService.getAllPermissionByOrgIdAndUserId(authDetails.getOrganizationId(), authDetails.getUserId());
    }

    @GetMapping("/emp/{type}")
    public List<RoleDto> getRolesByEmpIdAndModule(HttpServletRequest httpRequest,
                                                  @PathVariable("type") String moduleType) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        return roleService.getRolesByEmpIdAndModule(authDetails.getUserId(), moduleType);
    }

    @PostMapping("/user/{userId}/role/{newRoleId}/role/{oldRoleId}")
    public void changeRole(@PathVariable("userId") Long userId, @PathVariable("newRoleId") Long newRoleId, @PathVariable("oldRoleId") Long oldRoleId, HttpServletRequest httpRequest) {
        authService.getAuthDetails(httpRequest);
        roleService.changeRoleByOldRoleId(userId, newRoleId, oldRoleId);
    }
}
