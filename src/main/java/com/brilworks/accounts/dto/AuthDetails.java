package com.brilworks.accounts.dto;

import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDetails {

    private Long organizationId;
    private Long userId;
    private List<PermissionsEnum> permissionList;
}
