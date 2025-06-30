package com.reinosoft.core;

import com.reinosoft.core.cache.RestControllersMap;
import com.reinosoft.core.cache.RestControllersMap.HttpMethod;
import com.reinosoft.core.cache.RestControllersMap.RestControllerImpl;
import com.reinosoft.utils.SLogger;
import com.reinosoft.web.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.reinosoft.core.cache.RestControllersMap.HttpMethod.*;

public abstract class Extractor {

    private Extractor() {}

    public static void exctract(final Class<?> clazz) {
        final var classes = ClassExplorer.retrieveClasses(clazz);

        for (final var className : classes)
            Arrays.stream(Objects.requireNonNull(getAnnotations(className)))
                    .toList()
                    .forEach(annotation -> {
                        if (annotation instanceof RestController) {
                            SLogger.info(Extractor.class, "Found RestController: " + className);
                            extractMethods(className);
                        }
                    });
    }

    private static void extractMethods(final String className) {
        for (final var method : Objects.requireNonNull(getMethods(className))) {
            final var requestMapping = getAnnotation(className, RequestMapping.class);

            var requestPath = requestMapping != null ? validPath(((RequestMapping) requestMapping).value()) : "";

            if (method.isAnnotationPresent(Get.class)) storePaths(method, GET, method.getAnnotation(Get.class).value(), className, requestPath);
            else if (method.isAnnotationPresent(Post.class)) storePaths(method, POST, method.getAnnotation(Post.class).value(), className, requestPath);
            else if (method.isAnnotationPresent(Put.class)) storePaths(method, PUT, method.getAnnotation(Put.class).value(), className, requestPath);
            else if (method.isAnnotationPresent(Patch.class)) storePaths(method, PATCH, method.getAnnotation(Patch.class).value(), className, requestPath);
            else if (method.isAnnotationPresent(Delete.class)) storePaths(method, DELETE, method.getAnnotation(Delete.class).value(), className, requestPath);
        }
    }

    private static void storePaths(final Method method, final HttpMethod httpMethod, String annotationPath, final String className, final String requestPath) {
        showLog(method.getName(), className);
        final var path = requestPath + validPath(annotationPath);

        RestControllersMap.add(String.format("%s-%s", path, httpMethod).toLowerCase(), RestControllerImpl.of(path, className, method.getName(), httpMethod));
        SLogger.info(Extractor.class, String.format("Registered: %s: %s [%s.%s]", httpMethod, path, className, method.getName()));
    }

    private static String validPath(final String value) {
        String validPath = value;

        if (value == null || value.isBlank()) return "/";

        if (!value.startsWith("/")) validPath = String.format("/%s", validPath);
        if (value.endsWith("/")) validPath = validPath.substring(0, validPath.length() - 1);
        if (value.contains(" ")) validPath = validPath.replace(" ", "");

        return validPath.trim();
    }

    private static Annotation[] getAnnotations(String className) {
        try {
            return Class.forName(className).getAnnotations();
        } catch (ClassNotFoundException e) {
            SLogger.error(Extractor.class, e);
            return new Annotation[0];
        }
    }

    private static Annotation getAnnotation(String className, Class<? extends Annotation> annotationClass) {
        try {
            return Class.forName(className).getAnnotation(annotationClass);
        } catch (ClassNotFoundException e) {
            SLogger.error(Extractor.class, e);
            return null;
        }
    }

    private static List<Method> getMethods(String className) {
        try {
            final var methods = Class.forName(className).getDeclaredMethods();
            if (methods.length > 0) return List.of(methods);
            return Collections.emptyList();
        } catch (ClassNotFoundException e) {
            SLogger.error(Extractor.class, e);
            return Collections.emptyList();
        }
    }

    private static void showLog(final String methodName, final String className) {
        SLogger.info(Extractor.class, "Found GET method: " + methodName + " in class: " + className);
    }
}
