package com.brilworks.accounts.services;

import com.brilworks.accounts.dto.LinkedInPostRequestDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public interface LinkedInService {
    String getLinkedInUri(Authentication auth);
    void getAccessToken(String code,String state);
    void postToLinkedIn(Authentication auth, LinkedInPostRequestDto linkedInPostRequestDto);
}
