package com.reinosoft.utils;

import com.reinosoft.banner.Banner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.System.out;

public class SLogger {

    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String WHITE = "\u001B[37m";
    private static final String RESET = "\u001B[0m";

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private SLogger() {

    }

    public static void log(final Class<?> clazz, final String message) {
        final var dateTime = LocalDateTime.now().format(DATE_TIME_FORMAT);
        out.printf(GREEN + "%15s " + YELLOW + "%-30s: " + WHITE + "%s%n" + RESET, dateTime, clazz.getName(), message);
    }

    public static void printBanner() {
        out.printf(YELLOW);
        out.println(Banner.getBanner());
        out.println(RESET);
    }

}
