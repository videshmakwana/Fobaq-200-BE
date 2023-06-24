package com.brilworks.accounts.auth;

import com.brilworks.accounts.exception.BaseException;

public class ConflictException extends BaseException {
    public ConflictException(Conflicts conflicts) {
        super(404, conflicts.getStatusCode(), conflicts.getErrorMessage(), conflicts.getDeveloperMessage());
    }
    public enum Conflicts {

        EMPLOYEE_ID_MISSING("4090105","EMPLOYEE_ID_MISSING" ,"employee id missing" ),
        NOT_AUTHORIZED("4090107","NOT_AUTHORIZED" ,"Not auhtorized" );

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
        void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }
        String getErrorMessage() {
            return errorMessage;
        }

        void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getDeveloperMessage() {
            return developerMessage;
        }

        void setDeveloperMessage(String developerMessage) {
            this.developerMessage = developerMessage;
        }
    }
}