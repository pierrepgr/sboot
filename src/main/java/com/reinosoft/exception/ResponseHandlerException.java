package com.reinosoft.exception;

import java.io.Serial;

public class ResponseHandlerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4875987867629284624L;

    public ResponseHandlerException(Exception e) {
        super(e);
    }
}
