package com.brilworks.accounts.role_permissions.dto;

import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionDto {

    private Long id;

    private String name;

    private String description;

    private PermissionsEnum permissionKey;

    private ModuleType moduleType;

    private String permissionType;

    private String createdAt;

    public Permission dtoToEntity(PermissionDto permissionDto) {
        Permission permission = new Permission();
        if (permissionDto.getId() != null) {
            permission.setId(permissionDto.getId());
        }
        permission.setName(permissionDto.getName());
        permission.setPermissionKey(permissionDto.getPermissionKey());
        permission.setModuleType(permissionDto.getModuleType());
        return permission;
    }

    public PermissionDto entityToDto(Permission permission) {
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(permission.getId());
        permissionDto.setName(permission.getName());
        permissionDto.setPermissionKey(permission.getPermissionKey());
        permissionDto.setModuleType(permission.getModuleType());
        return permissionDto;
    }
}
