package com.reinosoft.exception;

import java.io.Serial;

public class ClassNotfoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 195577148475366776L;

    public ClassNotfoundException(String message, Exception e) {
        super(message, e);
    }
}
