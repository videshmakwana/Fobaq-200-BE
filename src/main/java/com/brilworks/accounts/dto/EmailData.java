package com.brilworks.accounts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailData {

    private String emailTo;

    private Long empId;

    private Long orgId;
    private String organisationUrl;
}
