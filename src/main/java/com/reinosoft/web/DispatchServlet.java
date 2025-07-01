package com.reinosoft.web;

import com.reinosoft.exception.MethodNotFoundException;
import com.reinosoft.utils.SLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

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
            resp.sendError(404, "Not Found");
        } catch (Exception e) {
            SLogger.error(this.getClass(), e);
            resp.sendError(500, e.getMessage());
        }
    }
}
