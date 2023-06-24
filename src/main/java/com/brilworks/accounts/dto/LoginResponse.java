package com.brilworks.accounts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1857935915480495789L;
    private Long userId;
    private String email;
    private String token;
    private String userName;
    private Long orgId;

    public LoginResponse(Long id, String email, String accessToken, String userName, Long orgId) {
        this.userId = id;
        this.email = email;
        this.token = accessToken;
        this.userName = userName;
        this.orgId = orgId;
    }
}
