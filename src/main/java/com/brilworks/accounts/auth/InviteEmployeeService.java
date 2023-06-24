package com.brilworks.accounts.auth;


import com.brilworks.accounts.auth.emp.InviteDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class InviteEmployeeService {

    @Value("${service.bricrm.url}")
    private String serviceUrl;

    public HttpStatus inviteInBRILCRM(InviteDto inviteDto, String token) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<AuthDetails> result = restTemplate
                    .exchange(new URI(serviceUrl+"/org/emp/invite"),
                            HttpMethod.POST,
                            new HttpEntity<>(inviteDto, getHttpHeaders(token)),
                            AuthDetails.class);
            return result.getStatusCode();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return headers;
    }
}
