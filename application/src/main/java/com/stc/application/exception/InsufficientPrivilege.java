package com.stc.application.exception;

public class InsufficientPrivilege extends Exception {

    private String errorMessage;

    public InsufficientPrivilege() {
        super();
    }

    public InsufficientPrivilege(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}