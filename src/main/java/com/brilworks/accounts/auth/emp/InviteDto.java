package com.brilworks.accounts.auth.emp;

import com.brilworks.accounts.role_permissions.dto.UserBasicInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InviteDto extends UserBasicInfo {
    private Long roleId;
}
