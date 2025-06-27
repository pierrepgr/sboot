package com.reinosoft.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class DispatchServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var writer = new PrintWriter(resp.getWriter());
        writer.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>ReinoSoft Web Application</title>
                </head>
                <body>
                    <h1>Welcome to ReinoSoft Web Application!</h1>
                    <p>This is a simple servlet response.</p>
                </body>
                </html>
                """);

        writer.close();
    }
}
