package com.brilworks.accounts.utils;


import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import com.brilworks.accounts.dto.AccessTokenModel;
import com.brilworks.accounts.dto.CustomUserDetail;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.exception.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class EmployeeUtils {

    @Autowired
    private TokenStoreService tokenStoreService;


    public User validateEmployee(Authentication auth) {
        User user = getUser(auth);
        if (user != null) {
            return user;
        } else {
            throw new AuthorizationException(Constants.NOT_AUTHORIZE);
        }
    }

    public User getUser(Authentication auth) {
        User user = null;
        if (auth != null) {
            CustomUserDetail customUserDetail = (CustomUserDetail) auth.getPrincipal();
            if (customUserDetail != null && customUserDetail.getUser() != null) {
                user = customUserDetail.getUser();
            }
        }
        return user;
    }

    public void setEmployee(Authentication auth, User employee, String token) {
        if (auth != null) {
            CustomUserDetail customUserDetail = (CustomUserDetail) auth.getPrincipal();
            if (customUserDetail != null) {
                customUserDetail.setUser(employee);
                tokenStoreService.updateUser(employee, token);
            }
        }
    }


    public void updateEmployee(Authentication auth, User userToUpdate, String token) {
        AccessTokenModel accessTokenModel = this.tokenStoreService.readAccessToken(token);
        if (auth != null && accessTokenModel != null) {
            User employee = accessTokenModel.getUser();
            if (!isSameEmployee(employee, userToUpdate)) {
                CustomUserDetail customUserDetail = (CustomUserDetail) auth.getPrincipal();
                if (customUserDetail != null) {
                    customUserDetail.setUser(userToUpdate);
                    tokenStoreService.updateUser(userToUpdate, token);
                }
            }
        }
    }

    public boolean isSameEmployee(User empOne, User empTwo) {
        if (empOne == empTwo) {
            return true;
        }
        return (empOne.getEmail() == null ? empTwo.getEmail() == null
                : empOne.getEmail().equals(empTwo.getEmail()))
                && (empOne.getUserName() == null ? empTwo.getUserName() == null
                : empOne.getUserName().equals(empTwo.getUserName()))
                && (empOne.getPassword() == null ? empTwo.getPassword() == null
                : empOne.getPassword().equals(empTwo.getPassword()));
    }

}
