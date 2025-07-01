package com.reinosoft.web;

import com.reinosoft.exception.ResponseHandlerException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

public interface ResponseHandler {

    void handleResponse(Object response, HttpServletResponse resp, PrintWriter writer) throws ResponseHandlerException;
}
