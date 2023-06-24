package com.brilworks.accounts.exception;


public class ConflictException extends BaseException {

    private static final long serialVersionUID = -6259195997762206266L;

    public ConflictException() {
        this(UserExceptionConflictMsg.USER_ALREADY_EXIST);
    }

    public ConflictException(Conflicts conflicts) {
        super(404, conflicts.getStatusCode(), conflicts.getErrorMessage(), conflicts.getDeveloperMessage());
    }

    public ConflictException(UserExceptionConflictMsg userExceptionMsg) {
        super(409, userExceptionMsg.getStatusCode(), userExceptionMsg.getErrorMessage(), userExceptionMsg.getDeveloperMessage());
    }

    public enum Conflicts {
        WORK_FLOW_SAVED_ONTHER_PLACED("4090101", "Work flow saved in some projects", "Work flow saved in some projects"),
        TASK_TEMPLATE_ALREADY_SAVED_IN_THIS_CHECKLIST("4090102", "Some task template saved in this check list, so you can not delete this checklist", "Some task template saved in this check list, so you can not delete this checklist"),
        TASK_TEMPLATE_ID_MISSING("4090103", "Task template id missing", "Task template id missing"),
        TRIGGER_ID_MISSING("4090104", "Trigger id missing", "Trigger id missing"),
        NOT_AUTHORIZED("4090105", "NOT_AUTHORIZED", "Not Authorized"),
        EMP_ID_MISSING("4090105", "Employee id missing", "Employee id missing"),
        NOT_AUTHORIZED_PLEASE_SIGN_UP("4090106", "Please complete your registration first with sign up link","Please complete your registration first with sign up link" );
        private String statusCode;
        private String errorMessage;
        private String developerMessage;

        Conflicts(String statusCode, String errorMessage, String developerMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
            this.developerMessage = developerMessage;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getDeveloperMessage() {
            return developerMessage;
        }

        public void setDeveloperMessage(String developerMessage) {
            this.developerMessage = developerMessage;
        }

    }

    public enum UserExceptionConflictMsg {
        USER_ALREADY_EXIST("4090101", "User already exist", "User already exist"),
        INCORRECT_PASSWORD("4090102", "Incorrect password, please try again.", "Incorrect password, please try again."),
        EMAIL_NOT_MATCH_PASSWORD(
                "4090103",
                "Email and password do not match",
                "Email and password do not match"),
        EMPTY_PASSWORD("4090104", "Password is Empty", "Password is Empty"),
        EMPTY_EMAIL("4090105", "Email is Empty", "Email is Empty"),
        EMAIL_NOT_MATCH_WITH_USER(
                "4090106",
                "email address not match with userd id",
                "email address not match with userd id"),
        EMAIL_NOT_MATCH_WITH_USER_ID(
                "4090123",
                "email address not match with userd id [user_id]",
                "email address not match with userd id [user_id]"),
        EMAIL_ALREADY_ATTACH_TO_PHONE_NUMBER(
                "4090107",
                "This email is already associated with another phone number",
                "This email is already associated with another phone number"),
        PHONE_NUMBER_ALREADY_ATTACH_TO_EMAIL(
                "4090108",
                "This phone number is already associated with another email address",
                "This phone number is already associated with another email address"),
        EMAIL_ALREADY_REGISTERED(
                "4090109",
                "User with email is already registered",
                "User with email is already registered"),
        ALREADY_STAFF(
                "4090110",
                "You have already added this team member.",
                "You have already added this team member."),
        EVENT_STRIPE_ACCOUNT_CONNECT_TOKEN_ALREADY_USED("4090111", "Token is already used", "Token is already used"),
        EVENT_URL_ALREADY_EXIST("4090112", "Event URL is already in use. Please try different URL.", "Event URL is already in use. Please try different URL."),
        USER_COUNTRY_ALREADY_PRESENT("4090113", "User country code is different", "User country code is different"),
        USER_BIDDERNUMBER_ALREADY_PRESENT("4090114", "Bidder number %d already registered with this email or phone number.", "Bidder number %d already registered with this email or phone number."),
        EMPTY_ORGANISATION_NAME("4090115", "Organisation name empty.", "Organisation name empty"),
        ORGANIZER_URL_ALREADY_EXIST("4090116", "Organizer URL is already in use.", "Organizer URL is already in use."),
        ORGANIZER_ALREADY_CREATED("4090118", "Organizer already created", "Organizer already created"),
        EVENT_NAME_ALREADY_EXIST("4090117", "Event name is already in use. Please try another name.", "Event name is already in use. Please try another name."),
        PHONE_NUMBER_ALREADY_ATTACH_TO_DIFFERENT_ACCOUNT(
                "4090118",
                "This phone number is already associated with a different account. A number can only be associated with one account.",
                "This phone number is already associated with a different account. A number can only be associated with one account."),
        EVENT_URL_ALREADY_EXIST_AND_SAME_ADMIN("4090119",
                "It looks like you are already using that URL for a different event. If you would like to use that URL for this event then you can access the other event and change the URL in the other event before attempting to change it in this event.",
                "It looks like you are already using that URL for a different event. If you would like to use that URL for this event then you can access the other event and change the URL in the other event before attempting to change it in this event."),
        EMAIL_ALREADY_ASSIGNEDED(
                "4090120",
                "This email address is already assigned. You can not change existing staff with this email address. Please add new staff if you want to add that email as admin/staff.",
                "This email address is already assigned. You can not change existing staff with this email address. Please add new staff if you want to add that email as admin/staff."),
        USER_HAS_ALREADY_LOGGED_IN("4090121", "User has already logged in at website, Can't change email now!", "User has already logged in at website, Can't change email now!"),
        PHONE_NUMBER_ALREADY_ATTACH_TO_EMAIL_WITH_PARAMETER(
                "4090121",
                "This phone number [phone_number] is already associated with another email address",
                "This phone number [phone_number] is already associated with another email address"),
        ROLE_ALREADY_PRESENT(
                "4090122",
                "Role Already Present",
                "Role Already Present"),
        EMAIL_ALREADY_ATTACH_TO_PHONE_NUMBER_WITH_PARAMETER(
                "4090123",
                "This email is already associated with [phone_number] phone number",
                "This email is already associated with [phone_number] phone number"),
        PHONE_NUMBER_ALREADY_ATTACH_TO_EMAIL_WITH_EMAIL_PARAMETER(
                "4090108",
                "This phone number is already associated with  [email_address] email address",
                "This phone number is already associated with  [email_address] email address"),
        DEPARTMENT_ALREADY_STORED_WITH_OTHER_EMPLOYEE("4090109", "Department already stored with other employee", "Department already stored with other employee"),
        EMAIL_ALREADY_SAVE("4090110", "Email already saved, try another one", "Email already saved, try another one");

        private String statusCode;
        private String errorMessage;
        private String developerMessage;

        UserExceptionConflictMsg(String statusCode, String errorMessage, String developerMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
            this.developerMessage = developerMessage;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getDeveloperMessage() {
            return developerMessage;
        }

        public void setDeveloperMessage(String developerMessage) {
            this.developerMessage = developerMessage;
        }

    }


}
