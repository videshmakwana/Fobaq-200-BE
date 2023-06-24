package com.brilworks.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LinkedInPostRequestDto {
    private String caption;
    private LinkedInMediaType linkedInMediaType;
    private LinkedInVisibilty linkedInVisibilty;
    private String author;
}
