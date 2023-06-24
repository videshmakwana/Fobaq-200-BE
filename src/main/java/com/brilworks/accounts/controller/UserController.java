package com.brilworks.accounts.controller;


import com.brilworks.accounts.auth.BrilEmpMS;
import com.brilworks.accounts.auth.emp.EmployeeOrgBasic;
import com.brilworks.accounts.auth.emp.InviteDto;
import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import com.brilworks.accounts.dto.EmailData;
import com.brilworks.accounts.dto.OrganisationDto;
import com.brilworks.accounts.dto.ResponseDto;
import com.brilworks.accounts.dto.SetPasswordRequest;
import com.brilworks.accounts.role_permissions.dto.AuthDetails;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.services.AuthService;
import com.brilworks.accounts.services.OrganisationService;
import com.brilworks.accounts.services.UserService;
import com.brilworks.accounts.services.impl.MyEmailService;
import com.brilworks.accounts.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/rest/accounts/module/{type}")
@RestController
public class UserController {

    @Autowired
    private BrilEmpMS brilEmpMS;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private MyEmailService myEmailService;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private TokenStoreService tokenStoreService;

    @Transactional
    @PostMapping("/user/invite")
    public EmployeeOrgBasic inviteUser(@PathVariable("type") String moduleType, @RequestBody InviteDto inviteDto, HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        authService.isAdmin(httpRequest, ModuleType.valueOf(moduleType));
        String authToken = httpRequest.getHeader(Constants.AUTHORIZATION);
        EmployeeOrgBasic employeeOrgBasic = userService.inviteUser(inviteDto, authDetails.getOrganizationId(), ModuleType.valueOf(moduleType));
        EmployeeOrgBasic employee = brilEmpMS.createEmployeeInviteInModule(employeeOrgBasic, ModuleType.valueOf(moduleType), authToken);
        OrganisationDto organisation = organisationService.findById(authDetails.getOrganizationId());

        EmailData emailData = new EmailData();
        emailData.setEmailTo(employee.getEmail());
        emailData.setEmpId(employee.getEmpId());
        emailData.setOrganisationUrl(organisation.getOrganisationUrl());
        emailData.setOrgId(authDetails.getOrganizationId());
        myEmailService.sendMail(emailData);
        return employee;
    }

    @PutMapping("/user/{id}/{isSignUp}")
    public ResponseDto enableOrDisableUser(@PathVariable("id") Long empId, @PathVariable("isSignUp") Boolean isSignUp, HttpServletRequest servletRequest) {
        authService.getAuthDetails(servletRequest);
        userService.enableOrDisableUser(isSignUp, empId);
        return ResponseDto.success();
    }

    @PostMapping("/user/reInvite/{userId}/{empId}")
    public ResponseDto reInviteUser(@PathVariable("type") String moduleType, @PathVariable("empId") Long empId, @PathVariable("userId") Long userId, HttpServletRequest httpRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpRequest);
        authService.isAdmin(httpRequest, ModuleType.valueOf(moduleType));
        userService.sendInviteLinkAgain(empId, authDetails.getOrganizationId(), userId);
        return new ResponseDto(Constants.SUCCESS, Constants.LINK_SEND);
    }

    @PostMapping("/setPassword/{userId}")
    public void setPasswordSignUpTime(@PathVariable("userId") Long userId, @RequestBody SetPasswordRequest setPassword) {
        userService.setPasswordSignUpTime(userId, setPassword);
    }
}
