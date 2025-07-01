package com.reinosoft.utils;

import com.reinosoft.exception.ClassNotfoundException;

public class ClassUtils {

    private ClassUtils() {}

    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ClassNotfoundException(String.format("Class not found: %s", className), e);
        }
    }
}
