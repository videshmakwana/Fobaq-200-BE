package com.brilworks.accounts.auth.emp;

import com.brilworks.accounts.controller.RecStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeOrgBasic extends InviteDto {
    private Long brilUserId;
    private RecStatus status;
    private Long empId;
    private Long roleId;
    private Long orgId;

    public EmployeeOrgBasic(InviteDto inviteDto) {
        setEmail(inviteDto.getEmail());
        setFirstName(inviteDto.getFirstName());
        setLastName(inviteDto.getLastName());
        setRoleId(inviteDto.getRoleId());
    }
}
