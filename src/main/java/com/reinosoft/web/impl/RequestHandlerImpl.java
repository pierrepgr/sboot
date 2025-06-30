package com.reinosoft.web.impl;

import com.reinosoft.core.cache.RestControllersMap.RestControllerImpl;
import com.reinosoft.exception.RequestHandlerException;
import com.reinosoft.web.MethodResolver;
import com.reinosoft.web.RequestHandler;
import com.reinosoft.web.RestControllerInstanceResolver;
import com.reinosoft.web.RestControllerResolver;
import jakarta.servlet.http.HttpServletRequest;

public class RequestHandlerImpl implements RequestHandler {

    private final MethodResolver methodResolver;
    private final RestControllerResolver restControllerResolver;
    private final RestControllerInstanceResolver restControllerInstanceResolver;

    public RequestHandlerImpl(MethodResolver methodResolver,
                              RestControllerResolver restControllerResolver,
                              RestControllerInstanceResolver restControllerInstanceResolver) {
        this.methodResolver = methodResolver;
        this.restControllerResolver = restControllerResolver;
        this.restControllerInstanceResolver = restControllerInstanceResolver;
    }

    @Override
    public Object handleRequest(HttpServletRequest request) throws RequestHandlerException {
        final var restControllerImpl = getRestController(request);
        final var restControllerInstance = restControllerInstanceResolver.getRestControllerInstance(restControllerImpl.getClassName()).orElseThrow(() -> new RequestHandlerException(String.format("Controller instance not found for class: %s", restControllerImpl.getClassName())));

        try {
           return restControllerImpl.getMethod().invoke(restControllerInstance);
        } catch (Exception e) {
            throw new RequestHandlerException(String.format("Error invoking method %s on controller %s", restControllerImpl.getMethod().getName(), restControllerImpl.getClassName()), e);
        }
    }

    private RestControllerImpl getRestController(HttpServletRequest request) {
        String key = request.getMethod() + request.getRequestURI();
        return restControllerResolver.findControllerByKey(key)
                .orElseThrow(() -> new RequestHandlerException(String.format("Controller not found for key: %s", key.toLowerCase())));
    }
}
