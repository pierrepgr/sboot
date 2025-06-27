package com.reinosoft.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2938950426896303169L;

    private final String value;

    public BusinessException(String value, Exception exception) {
        super(value, exception);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
