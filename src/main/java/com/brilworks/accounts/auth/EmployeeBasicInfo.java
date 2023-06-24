package com.brilworks.accounts.auth;

import com.brilworks.accounts.auth.emp.UserBasicInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeBasicInfo extends UserBasicInfo {

    private Long brilUserId;
    private String firstName;
    private String lastName;
    private String email;
    private Long orgId;
}
