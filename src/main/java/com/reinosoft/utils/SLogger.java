package com.reinosoft.utils;

import com.reinosoft.banner.Banner;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.reinosoft.utils.LogType.*;
import static java.lang.System.out;

public class SLogger {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final StringWriter sw;
    private static final PrintWriter pw;

    static {
        sw = new StringWriter();
        pw = new PrintWriter(sw);
    }

    private SLogger() {

    }

    public static void error(final Class<?> clazz, final String message) {
        log(ERROR, clazz, message);
    }

    public static void error(final Class<?> clazz, final Exception exception) {
        exception.printStackTrace(pw);
        log(ERROR, clazz, sw.toString());
    }


    public static void info(final Class<?> clazz, final String message) {
        log(INFO, clazz, message);
    }

    public static void warning(final Class<?> clazz, final String message) {
        log(WARNING, clazz, message);
    }

    public static void success(final Class<?> clazz, final String message) {
        log(SUCCESS, clazz, message);
    }

    private static void log(final LogType logType, final Class<?> clazz, final String message) {
        final var dateTime = LocalDateTime.now().format(DATE_TIME_FORMAT);

        if (logType == ERROR) {
            out.printf(String.format("%s%12s %8s ---%-35s: %s%n%s", ERROR.getColorCode(), dateTime, logType, clazz.getName(), message, DEFAULT.getColorCode()));
        } else {
            out.printf(String.format("%s%12s %s%8s ---%s %-35s: %s%s%n%s", SUCCESS.getColorCode(), dateTime, logType.getColorCode(), logType, WARNING.getColorCode(), clazz.getName(), DEFAULT.getColorCode(), message, DEFAULT.getColorCode()));
        }
    }

    public static void printBanner() {
        out.printf(SUCCESS.getColorCode());
        out.println(Banner.getBanner());
        out.println(DEFAULT.getColorCode());
    }

}
