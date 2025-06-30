package com.reinosoft.web.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinosoft.exception.ResponseHandlerException;
import com.reinosoft.utils.SLogger;
import com.reinosoft.web.ResponseHandler;

import java.io.PrintWriter;

public class ResponseHandlerImpl implements ResponseHandler {

    private final ObjectMapper mapper;

    public ResponseHandlerImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handleResponse(Object response, PrintWriter writer) throws ResponseHandlerException {
        try {
            final var responseWriter = new PrintWriter(writer);
            responseWriter.println(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            SLogger.error(ResponseHandlerImpl.class, e);
            throw new ResponseHandlerException(e);
        }
    }
}
