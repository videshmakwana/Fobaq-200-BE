package com.brilworks.accounts.controller;

import com.brilworks.accounts.dto.LinkedInPostRequestDto;
import com.brilworks.accounts.services.LinkedInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/api/linkedin")
@Controller
public class LinkedInAuthController {
    private static final Logger log = LoggerFactory.getLogger(LinkedInAuthController.class);
    @Autowired
    LinkedInService linkedInService;




    @RequestMapping(value="getLinkedInUri", method = RequestMethod.GET)
    @ResponseBody
    public String getLinkedInUri(Authentication auth) {
        return linkedInService.getLinkedInUri(auth);
    }

    @RequestMapping(value = "oauth2/token", method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE})
    @ResponseBody
    public void retriveAccessTokenForLinkedIn(
            @RequestParam Map<String, String> bodyParameters) {
        bodyParameters.entrySet().stream().forEach(e->System.out.println("key : "+e.getKey()+", value : "+e.getValue()));
        if(bodyParameters.containsKey("error_description")){
            log.error("error occured in "+bodyParameters.get("error_description"));
            return;
        }
        if(bodyParameters.containsKey("code")){
            String state = bodyParameters.containsKey("state") ? bodyParameters.get("state") : null;
            String code = bodyParameters.get("code");
            linkedInService.getAccessToken(code,state);
        }
    }

    @RequestMapping(value = "postToLinkedIn",method = RequestMethod.POST)
    @ResponseBody
    public void postToLinkedIn(Authentication auth, @RequestBody LinkedInPostRequestDto linkedInPostRequestDto){
        linkedInService.postToLinkedIn(auth, linkedInPostRequestDto);
    }
}
