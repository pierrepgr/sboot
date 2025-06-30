package com.reinosoft.core.cache;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RestControllersMap {

    private static final ConcurrentMap<String, RestControllerImpl> restControllers = new ConcurrentHashMap<>();

    private RestControllersMap() {}

    public static void add(final String key, final RestControllerImpl value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value must not be null");
        }

        if (restControllers.containsKey(key)) {
            throw new IllegalArgumentException("Key already exists: " + key);
        }

        restControllers.putIfAbsent(key, value);
    }

    public static ConcurrentMap<String, RestControllerImpl> getRestControllers() {
        return restControllers;
    }

    public static class RestControllerImpl {

        private final String path;
        private final Method method;
        private final String className;
        private final HttpMethod httpMethod;

        private RestControllerImpl(final String path,
                                   final Method method,
                                   final String className,
                                   final HttpMethod httpMethod) {
            this.path = path;
            this.method = method;
            this.className = className;
            this.httpMethod = httpMethod;
        }

        public static RestControllerImpl of(final String path,
                                            final Method method,
                                            final String className,
                                            final HttpMethod httpMethod) {
            return new RestControllerImpl(path, method, className , httpMethod);
        }

        public String getPath() {
            return path;
        }
        public String getClassName() {
            return className;
        }
        public Method getMethod() {
            return method;
        }
        public HttpMethod getHttpMethod() {
            return httpMethod;
        }
    }

    public enum HttpMethod {
        GET, POST, PUT, DELETE, PATCH;
    }
}
