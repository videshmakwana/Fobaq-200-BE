package com.brilworks.accounts.role_permissions.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleWithListOfPermissionDto {
    private RoleDto role;
    private List<PermissionDto> permissionList;
}
