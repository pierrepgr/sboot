package com.reinosoft.web;

import com.reinosoft.exception.ResponseHandlerException;
import com.reinosoft.web.error.Error;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

public interface ResponseHandler {

    void handleResponse(Object response, HttpServletResponse resp, PrintWriter writer) throws ResponseHandlerException;

    void handleResponseError(Error error, HttpServletResponse resp, PrintWriter writer) throws ResponseHandlerException;
}
