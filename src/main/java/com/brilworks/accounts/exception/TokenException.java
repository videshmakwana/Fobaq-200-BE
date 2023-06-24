package com.brilworks.accounts.exception;

public class TokenException extends BaseException {
    private static final long serialVersionUID = 1L;

    public TokenException(Exceptions exceptions) {
        super(404, exceptions.getStatusCode(), exceptions.getErrorMessage(),exceptions.getDeveloperMessage());
    }

    public enum Exceptions {
        ERROR_IN_GENERATION_OF_TOKEN("404001", "Error not generate", "Error not generate");
        private final String statusCode;
        private final String errorMessage;
        private final String developerMessage;

        Exceptions(String statusCode, String errorMessage, String developerMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
            this.developerMessage= developerMessage;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getDeveloperMessage() {
            return developerMessage;
        }
    }
}
