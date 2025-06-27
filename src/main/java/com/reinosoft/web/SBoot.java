package com.reinosoft.web;

import com.reinosoft.utils.SLogger;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SBoot {
    private static final String SBOOT_VERSION = "1.0.0";

    private static final String SERVLET_NAME = "dispatcher";
    private static final String CONTEXT_PATH = "/*";

    private static Tomcat tomcat;

    private SBoot() {}

    public static void run() {
        SLogger.printBanner();
        SLogger.info(SBoot.class, "Starting Application");

        final var startUpInitTime = System.currentTimeMillis();
        startTomcat();
        final var startupEndTime = System.currentTimeMillis();

        SLogger.info(SBoot.class, "ReinoSoft Web Application started in: " + (startupEndTime - startUpInitTime) + "ms");
        tomcat.getServer().await();
    }

    private static void startTomcat() {
        disableApacheLogs();

        tomcat = new Tomcat();

        final var connector = new Connector();
        connector.setPort(8080);
        connector.setThrowOnFailure(true);

        tomcat.setConnector(connector);

        final var context = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, SERVLET_NAME, new DispatchServlet());
        context.addServletMappingDecoded(CONTEXT_PATH, SERVLET_NAME);

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            SLogger.error(SBoot.class, e);
            System.exit(1);
        }
    }

    private static void disableApacheLogs() {
        Logger.getLogger("org.apache").setLevel(Level.OFF);
    }

    public static String getVersion() {
        return SBOOT_VERSION;
    }
}
