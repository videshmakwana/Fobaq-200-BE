package com.brilworks.accounts.dto;

import com.brilworks.accounts.role_permissions.dto.UserBasicInfo;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest extends UserBasicInfo {
    private String userName;
    private String organisationName;
    private ModuleType moduleType;
}
