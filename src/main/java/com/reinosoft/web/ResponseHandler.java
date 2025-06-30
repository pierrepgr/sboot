package com.reinosoft.web;

import com.reinosoft.exception.ResponseHandlerException;

import java.io.PrintWriter;

public interface ResponseHandler {

    void handleResponse(Object response, PrintWriter writer) throws ResponseHandlerException;
}
