package com.brilworks.accounts.utils;

import org.springframework.stereotype.Component;

@Component
public interface Constants {
    String ATOZ_BOTHCASE_AND_NUMBERS = "[^a-zA-Z0-9]";
    String STRING_EMPTY = "";
    String ADMIN_ROLE = "ADMIN_ROLE";
    String DELETED = "Deleted successfully";
    String SAVED = "Saved successfully";
    String ADDED = "Added successfully";
    String UPDATED = "Updated successfully";
    String AUTHORIZATION = "Authorization";

    String TYPE = "Type";
    String MAX_LIMIT_REACHED = "Your account has been locked to protect your security. Please <a href='/u/password-reset'> reset your password</a> or try again after 30 minutes.";
    String SUCCESS = "Success";
    String NOT_AUTHORIZE = "Not authorize to access";
    String PASSWORD_NOT_MATCH = "Password doesn't match";
    String DELETE = "DELETE";
    String ERRORS = "errors";
    String ERROR = "error";
    String LIKE = "You liked";
    String LINK_SEND = "Invite link send successfully";
    String SIGN_IN = "Sign-up successfully";
    String USER_INVITED = "Invited successfully";
    String PASSWORD_CHANGED = "Password changed successfully";
    String USER_NAME = "userName";
    String REQUEST_ID = "requestId";
    String NA = "NA";
    String USER_DETAILS = "userdetail";
    String TRUE = "true";
    String SERVER = "Server";
    String ORIGIN = "Origin";
    String FORGET_PASSWORD_LINK_SEND = "Forget password link sent to your register email address";
    String COMPANY_MAIL = "khushbu.s@brilworks.com";
    String COMMENT = "You commented";
    String CHANGED = "Change successfully";
    String ASSIGNEE = "ASSIGNEE";
    String MANAGER = "MANAGER";
    String INVITED = "INVITED";
    String ACCESS_KEY = "AKIAZDZPEA74OEXEVDOB";
    String SECRET_KEY = "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";
    String TASK_FILES = "task_files";
    String AWS_REGION = "ap-south-1";
    String UI_BASE_URL = "AAAAAA";
    String EVENT_BASE_URL = "eventBaseUrl";
}
