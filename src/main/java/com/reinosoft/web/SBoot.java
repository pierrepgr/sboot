package com.reinosoft.web;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class SBoot {
    private static final String SERVLET_NAME = "dispatcher";
    private static final String CONTEXT_PATH = "/*";

    private SBoot() {}

    public static void run() {
        final var tomcat = new Tomcat();
        final var connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        final var context = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, SERVLET_NAME, new DispatchServlet());
        context.addServletMappingDecoded(CONTEXT_PATH, SERVLET_NAME);

        try {

            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        } finally {
            tomcat.getServer().await();
        }
    }
}
