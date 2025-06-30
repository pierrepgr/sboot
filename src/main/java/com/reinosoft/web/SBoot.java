package com.reinosoft.web;

import com.reinosoft.core.Extractor;
import com.reinosoft.exception.BusinessException;
import com.reinosoft.utils.SLogger;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SBoot {
    private static final String SBOOT_VERSION = "1.0.0";
    private static final String SERVLET_NAME = "dispatcher";
    private static final String PATTERN = "/*";
    private static final int PORT = 8080;

    private static final Tomcat tomcat = new Tomcat();

    private SBoot() {
    }

    public static void run(Class<?> clazz) {
        SLogger.printBanner();
        final var startUpInitTime = System.currentTimeMillis();
        SLogger.info(SBoot.class, "Starting Application");

        Extractor.exctract(clazz);
        disableApacheLogs();
        initialize();

        final var startupEndTime = System.currentTimeMillis();
        SLogger.info(SBoot.class, "ReinoSoft Web Application started in: " + (startupEndTime - startUpInitTime) + "ms");
        tomcat.getServer().await();
    }

    public static void initialize() {
        tomcat.setBaseDir(createTempDir().getAbsolutePath());

        final var connector = new Connector();
        connector.setPort(PORT);
        connector.setThrowOnFailure(true);
        tomcat.setConnector(connector);

        final var context = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(context, SERVLET_NAME, new DispatchServlet());
        context.addServletMappingDecoded(PATTERN, SERVLET_NAME);

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

    private static File createTempDir() {
        try {
            final var tempDir = Files.createTempDirectory("tomcat" + "." + PORT + ".sboot").toFile();
            tempDir.deleteOnExit();
            return tempDir;
        } catch (IOException e) {
            throw new BusinessException("Failed to create temporary directory for Tomcat", e);
        }
    }

    public static String getVersion() {
        return SBOOT_VERSION;
    }
}
