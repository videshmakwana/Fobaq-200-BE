package com.brilworks.accounts.role_permissions.services;

import com.brilworks.accounts.role_permissions.dto.PermissionDto;
import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface PermissionService {
    Permission findById(long empRole);

    Long createPermission(PermissionDto dto);

    Long updatePermission(PermissionDto dto);

    List<PermissionDto> getPermissions();

    Permission getPermissionByPermissionKey(String permissionKey);

    void deletePermission(long permissionId);

    Long findByModuleTypeAndPermissionKey(ModuleType moduleType, PermissionsEnum permissionsEnum);

    List<Permission> findByModuleType(ModuleType moduleType);
}
