package com.brilworks.accounts.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private final int status;
    private final String errorCode;
    private final String errorMessage;
    private final String developerMessage;

    protected BaseException(int status, String errorCode, String errorMessage, String developerMessage) {
        super(developerMessage);
        this.status=status;
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
        this.developerMessage=developerMessage;
    }
}
