package com.brilworks.accounts.role_permissions.services;

import com.brilworks.accounts.entity.Organisation;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.entity.Role;
import com.brilworks.accounts.role_permissions.entity.UserRole;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleService {

    Role getRoleDetailsByIdAndOrgId(Long roleId, Long orgId);

    List<Role> findByRoleIdsInOrganizationAndModule(Long orgId, ModuleType moduleType);

    Long createRole(RoleDto dto, Long orgId);

    Long updateRole(RoleDto dto);

    List<RoleDto> getRoles(Long orgId, String moduleType);

    void deleteRole(Long id);

    RoleDto findRoleDtoById(Long id, Long roleId);

    void handleDefaultRoleCreation(Organisation organisation, ModuleType moduleType, User user);

    List<Permission> getAllPermissionByOrgIdAndUserId(Long organizationId, Long userId);

    List<UserRole> getUserRoleByUserIdAndOrgId(Long userId, Long orgId);

    Role findRoleByRoleId(Long roleId);

    List<RoleDto> getRolesByEmpIdAndModule(Long userId, String moduleType);

    Role getRoleByOrgIdAndRoleType(Long orgId, ModuleType moduleType);

    void changeRoleByOldRoleId(Long userId, Long newRoleId, Long oldRoleId);

}
