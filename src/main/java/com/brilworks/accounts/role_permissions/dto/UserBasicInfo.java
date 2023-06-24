package com.brilworks.accounts.role_permissions.dto;

import com.brilworks.accounts.dto.LoginRequest;
import com.brilworks.accounts.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBasicInfo extends LoginRequest {

    private String firstName;
    private String lastName;

    public UserBasicInfo(User user) {
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
    }


}
