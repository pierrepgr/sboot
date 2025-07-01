package com.reinosoft.web.impl;

import com.reinosoft.core.cache.RestControllersMap.RestControllerImpl;
import com.reinosoft.exception.RequestHandlerException;
import com.reinosoft.web.RequestHandler;
import com.reinosoft.web.ComponentInstanceResolver;
import com.reinosoft.web.RestControllerResolver;
import jakarta.servlet.http.HttpServletRequest;

public class RequestHandlerImpl implements RequestHandler {

    private final RestControllerResolver restControllerResolver;
    private final ComponentInstanceResolver componentInstanceResolver;

    public RequestHandlerImpl(RestControllerResolver restControllerResolver,
                              ComponentInstanceResolver componentInstanceResolver) {
        this.restControllerResolver = restControllerResolver;
        this.componentInstanceResolver = componentInstanceResolver;
    }

    @Override
    public Object handleRequest(HttpServletRequest request) throws RequestHandlerException {
        final var restControllerImpl = getRestController(request);
        final var restControllerInstance = componentInstanceResolver.getComponentInstance(restControllerImpl.getClassName()).orElseThrow(() -> new RequestHandlerException(String.format("Controller instance not found for class: %s", restControllerImpl.getClassName())));

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
