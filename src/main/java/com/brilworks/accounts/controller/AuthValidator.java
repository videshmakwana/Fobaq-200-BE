package com.brilworks.accounts.controller;


import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.exception.AuthorizationException;
import com.brilworks.accounts.utils.Constants;
import com.brilworks.accounts.utils.EmployeeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {
    @Autowired
    private EmployeeUtils employeeUtils;


    @Autowired
    AuthValidator(EmployeeUtils employeeUtils) {
        this.employeeUtils = employeeUtils;
    }

    public User authEmployeeValid(Authentication auth) {
        return employeeUtils.getUser(auth);
    }

    public User authUser(Authentication auth) {
        User user = employeeUtils.getUser(auth);
        if (user == null) {
            throw new AuthorizationException(Constants.NOT_AUTHORIZE);
        }
        return user;
    }
    public Boolean authEmployeeBoolean(Authentication auth) {
        User user = employeeUtils.getUser(auth);
        if (user == null) {
           return false;
        }
        return true;
    }

    public User getEmployeeByAuth(Authentication auth) {
        return employeeUtils.getUser(auth);
    }

}
