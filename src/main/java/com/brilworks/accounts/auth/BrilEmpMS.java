package com.brilworks.accounts.auth;


import com.brilworks.accounts.auth.emp.EmployeeOrgBasic;
import com.brilworks.accounts.controller.RecStatus;
import com.brilworks.accounts.dto.LoginResponse;

import com.brilworks.accounts.dto.SignUpRequest;
import com.brilworks.accounts.role_permissions.entity.Role;
import com.brilworks.accounts.role_permissions.entity.UserRole;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.services.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@Service
public class BrilEmpMS {

    @Value("${service.bricrm.url}")
    private String serviceUrl;
    @Value("${service.okr.url}")
    private String okrUrl;
    @Value("${service.task.url}")
    private String taskUrl;



    public HttpStatus createEmployeeInBRILCRM(EmployeeOrgBasic employeeOrgBasic, String token) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<AuthDetails> result = restTemplate.exchange(new URI( serviceUrl+"/org/emp"), HttpMethod.POST, new HttpEntity<>(employeeOrgBasic, getHttpHeaders(token)), AuthDetails.class);
            return result.getStatusCode();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpStatus createEmployeeInOKR(EmployeeOrgBasic employeeOrgBasic, String token) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<AuthDetails> result = restTemplate.exchange(new URI(okrUrl + "/org/employee"), HttpMethod.POST, new HttpEntity<>(employeeOrgBasic, getHttpHeaders(token)), AuthDetails.class);
            return result.getStatusCode();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpStatus createEmployeeInTask(EmployeeOrgBasic employeeOrgBasic, String token) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<AuthDetails> result = null;
        try {
            result = restTemplate.exchange(new URI(taskUrl + "/org/employee"), HttpMethod.POST, new HttpEntity<>(employeeOrgBasic, getHttpHeaders(token)), AuthDetails.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result.getStatusCode();

    }


    public EmployeeOrgBasic createEmployeeInBRILCRM1(EmployeeOrgBasic employeeOrgBasic, String token) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Long> result = restTemplate.exchange(new URI(serviceUrl + "/org/emp/invite"), HttpMethod.POST, new HttpEntity<>(employeeOrgBasic, getHttpHeaders(token)), Long.class);
            employeeOrgBasic.setEmpId(result.getBody());
            return employeeOrgBasic;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmployeeOrgBasic createEmployeeInOKR1(EmployeeOrgBasic employeeOrgBasic, String token) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Long> result = restTemplate.exchange(new URI(okrUrl + "/org/employee/invite"), HttpMethod.POST, new HttpEntity<>(employeeOrgBasic, getHttpHeaders(token)), Long.class);
            employeeOrgBasic.setEmpId(result.getBody());
            return employeeOrgBasic;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmployeeOrgBasic createEmployeeInTask1(EmployeeOrgBasic employeeOrgBasic, String token) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Long> result = null;
        try {
            result = restTemplate.exchange(new URI(taskUrl + "/org/employee/invite"), HttpMethod.POST, new HttpEntity<>(employeeOrgBasic, getHttpHeaders(token)), Long.class);
            employeeOrgBasic.setEmpId(result.getBody());
            return employeeOrgBasic;
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

    @Transactional
    public void createEmployeeInModule(SignUpRequest signUpRequest, LoginResponse response, List<UserRole> listOfRole, RoleService roleService) {
        Role roleEmp = new Role();
        Role roleTask = new Role();
        Role roleOkr = new Role();
        Long orgId = 0L;
        for (UserRole userRole : listOfRole) {
            Role role = roleService.findRoleByRoleId(userRole.getRoleId());
            userRole.setRole(role);
            orgId = userRole.getOrganizationId();
            if (userRole.getRole() != null) {
                switch (userRole.getRole().getModuleType()) {
                    case EMP:
                        roleEmp = userRole.getRole();

                        break;
                    case OKR:
                        roleOkr = userRole.getRole();
                        break;
                    case TASK:
                        roleTask = userRole.getRole();
                        break;
                    default:
                        break;
                }
            }
        }
        EmployeeOrgBasic employeeOrgBasic = new EmployeeOrgBasic();
        employeeOrgBasic.setFirstName(signUpRequest.getFirstName());
        employeeOrgBasic.setLastName(signUpRequest.getLastName());
        employeeOrgBasic.setEmail(signUpRequest.getEmail());
        employeeOrgBasic.setBrilUserId(response.getUserId());
        employeeOrgBasic.setPassword(signUpRequest.getPassword());
        employeeOrgBasic.setOrgId(orgId);
        switch (signUpRequest.getModuleType()) {
            case TASK:
                employeeOrgBasic.setRoleId(roleTask.getId());
                createEmployeeInTask(employeeOrgBasic, response.getToken());
                break;
            case OKR:
                employeeOrgBasic.setRoleId(roleOkr.getId());
                createEmployeeInOKR(employeeOrgBasic, response.getToken());
                break;
            case EMP:
                employeeOrgBasic.setRoleId(roleEmp.getId());
                createEmployeeInBRILCRM(employeeOrgBasic, response.getToken());
                break;
            case ALL:
                employeeOrgBasic.setRoleId(roleTask.getId());
                createEmployeeInTask(employeeOrgBasic, response.getToken());
                employeeOrgBasic.setRoleId(roleOkr.getId());
                createEmployeeInOKR(employeeOrgBasic, response.getToken());
                employeeOrgBasic.setRoleId(roleEmp.getId());
                createEmployeeInBRILCRM(employeeOrgBasic, response.getToken());
                break;
        }

    }

    @Transactional
    public EmployeeOrgBasic createEmployeeInviteInModule(EmployeeOrgBasic employeeOrgBasic, ModuleType moduleType, String token) {
        {
            employeeOrgBasic.setStatus(RecStatus.INVITED);
            switch (moduleType) {
                case TASK:
                    createEmployeeInTask1(employeeOrgBasic, token);
                    break;
                case OKR:
                    createEmployeeInOKR1(employeeOrgBasic, token);
                    break;
                case EMP:
                    createEmployeeInBRILCRM1(employeeOrgBasic, token);
                    break;
                default:
                    break;
            }
            return employeeOrgBasic;
        }
    }
}
