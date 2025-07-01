package com.reinosoft.web.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinosoft.exception.ResponseHandlerException;
import com.reinosoft.utils.SLogger;
import com.reinosoft.web.ResponseHandler;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

public class ResponseHandlerImpl implements ResponseHandler {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private final ObjectMapper mapper;

    public ResponseHandlerImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handleResponse(Object response, HttpServletResponse resp, PrintWriter writer) throws ResponseHandlerException {
        try {
            final var responseWriter = new PrintWriter(writer);
            switch (response) {
                case String str when isValidJson(str) -> {
                    resp.setHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
                    responseWriter.println(response);
                }
                case String str -> {
                    resp.setHeader(CONTENT_TYPE, "text/plain");
                    responseWriter.println(str);
                }
                default -> {
                    resp.setHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
                    responseWriter.println(mapper.writeValueAsString(response));
                }
            }
        } catch (JsonProcessingException e) {
            SLogger.error(ResponseHandlerImpl.class, e);
            throw new ResponseHandlerException(e);
        }
    }

    private boolean isValidJson(final String json) {
        try {
            mapper.readTree(json);
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }
}
