package com.brilworks.accounts.controller;


import com.brilworks.accounts.dto.PasswordChangeRequest;
import com.brilworks.accounts.dto.ResponseDto;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.services.AuthService;
import com.brilworks.accounts.services.UserService;
import com.brilworks.accounts.utils.Constants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "brilworks")
@RestController
@RequestMapping("/rest/accounts/password")
public class PasswordController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PutMapping("/change")
    public ResponseDto changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(passwordChangeRequest);
        return new ResponseDto(Constants.SUCCESS, Constants.UPDATED);
    }

    @PostMapping("/forget/{email}")
    public ResponseDto generateForgetPasswordLink(@PathVariable("email") String email) {
        userService.generateForgetPasswordLink(email);
        return new ResponseDto(Constants.SUCCESS, Constants.FORGET_PASSWORD_LINK_SEND);
    }

    @GetMapping("/get-emp-by-token/{token}")
    public Long getUserInfoByToken(@PathVariable("token") String token) {
        User user = userService.getUserByToken(token);
        return user.getId();
    }
}
