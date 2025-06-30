package com.reinosoft.exception;

import java.io.Serial;

public class RequestHandlerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1033902248388514896L;

    public RequestHandlerException() {
        super();
    }

    public RequestHandlerException(String message) {
        super(message);
    }

    public RequestHandlerException(String message, Exception e) {
        super(message, e);
    }
}
