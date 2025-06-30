package com.reinosoft.core.cache;

import java.util.concurrent.ConcurrentHashMap;

public class RestControllersMap {

    private static final ConcurrentHashMap<String, RestControllerImpl> restControllers = new ConcurrentHashMap<>();

    public static boolean add(final String key, final RestControllerImpl value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value must not be null");
        }

        if (restControllers.containsKey(key)) {
            throw new IllegalArgumentException("Key already exists: " + key);
        }

        return restControllers.putIfAbsent(key, value) == null;
    }

    public static class RestControllerImpl {

        private final String path;
        private final String className;
        private final String methodName;
        private final HttpMethod httpMethod;

        private RestControllerImpl(final String path,
                                   final String className,
                                   final String methodName,
                                   final HttpMethod httpMethod) {
            this.path = path;
            this.className = className;
            this.methodName = methodName;
            this.httpMethod = httpMethod;
        }

        public static RestControllerImpl of(final String path,
                                            final String className,
                                            final String methodName,
                                            final HttpMethod httpMethod) {
            return new RestControllerImpl(path, className, methodName, httpMethod);
        }

        public String getPath() {
            return path;
        }
        public String getClassName() {
            return className;
        }
        public String getMethodName() {
            return methodName;
        }
        public HttpMethod getHttpMethod() {
            return httpMethod;
        }
    }

    public enum HttpMethod {
        GET, POST, PUT, DELETE, PATCH;
    }
}
