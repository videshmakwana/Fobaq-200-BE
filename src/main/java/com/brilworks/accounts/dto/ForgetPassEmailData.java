package com.brilworks.accounts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ForgetPassEmailData {
    private String email;
    private String token;
}
