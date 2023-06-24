package com.brilworks.accounts.role_permissions.services;

import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.dto.RoleWithListOfPermissionDto;
import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.entity.Role;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RolePermissionMappingService {
	void saveListOfPermissionWithRoleId(RoleWithListOfPermissionDto roleWithListOfPermissionDto, Long orgId);

	List<String> getPermissionListInArray(Long userId, Long organizationId, String moduleType);

	List<PermissionsEnum> getPermissionList(List<Long> roleIds);

	RoleWithListOfPermissionDto getRoleAndPermissionListByRoleId(Long id, Long orgId);

	void updateRolePermissionMapping(RoleWithListOfPermissionDto rolePermissionDto, Long organizationId);

	void deleteRolePermissionsOfRole(Long roleId);


	void mapAdminRoleWithPermission(Long roleId);

	void createDefaultPermission(Role role, PermissionsEnum permissionsEnum);

	List<Permission> getAllPermissionsOfModule(Long organizationId, ModuleType moduleType);

	List<RoleDto> getRoleIdByUserIdAndOrgId(Long userId, Long orgId);

	void deleteRolePermissionsOfRoleByRoleId(Long roleId);
}
