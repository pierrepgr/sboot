package com.reinosoft.web.error;

public class Error {

    private final int errorCode;
    private final String errorMessage;

    private Error(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static Error of(int errorCode, String errorMessage) {
        return new Error(errorCode, errorMessage);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
