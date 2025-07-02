package com.reinosoft.web;

import com.reinosoft.exception.MethodNotFoundException;
import com.reinosoft.exception.RestControllerNotFoundException;
import com.reinosoft.utils.SLogger;
import com.reinosoft.web.error.Error;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class DispatchServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = -6643960782980263275L;

    private final transient RequestHandler requestHandler;
    private final transient ResponseHandler responseHandler;

    public DispatchServlet(RequestHandler requestHandler, ResponseHandler responseHandler) {
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final var response = this.requestHandler.handleRequest(req);
            this.responseHandler.handleResponse(response, resp, resp.getWriter());
        } catch (MethodNotFoundException e) {
            SLogger.error(this.getClass(), e);
            this.responseHandler.handleResponseError(Error.of(SC_NOT_FOUND, String.format("Method not found: %s", e.getMessage())), resp, resp.getWriter());
        } catch (RestControllerNotFoundException e) {
            SLogger.error(this.getClass(), e);
            this.responseHandler.handleResponseError(Error.of(SC_NOT_FOUND, e.getMessage()), resp, resp.getWriter());
        } catch (Exception e) {
            SLogger.error(this.getClass(), e);
            this.responseHandler.handleResponseError(Error.of(SC_INTERNAL_SERVER_ERROR, String.format("Internal server error: %s", e.getMessage())), resp, resp.getWriter());
        }
    }
}
