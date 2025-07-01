package com.reinosoft.exception;

import java.io.Serial;

public class NewInstanceComponentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 112258240603951353L;

    public NewInstanceComponentException(String message, Exception e) {
        super(message, e);
    }
}
