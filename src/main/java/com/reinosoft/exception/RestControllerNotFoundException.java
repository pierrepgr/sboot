package com.reinosoft.exception;

import java.io.Serial;

public class RestControllerNotFoundException extends RequestHandlerException {
    @Serial
    private static final long serialVersionUID = 1282544298835986808L;

    public RestControllerNotFoundException(String message) {
        super(message);
    }
}
