package com.reinosoft.utils;

public enum LogType {

    INFO("\u001B[37m"),
    WARNING("\u001B[33m"),
    ERROR("\u001B[31m"),
    SUCCESS("\u001B[32m"),
    DEFAULT("\u001B[0m");

    private final String colorCode;

    LogType(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
