package com.reinosoft.web;

import com.reinosoft.exception.RequestHandlerException;
import jakarta.servlet.http.HttpServletRequest;

public interface RequestHandler {

    Object handleRequest(HttpServletRequest request) throws RequestHandlerException;
}
