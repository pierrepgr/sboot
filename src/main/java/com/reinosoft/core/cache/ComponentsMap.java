package com.reinosoft.core.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ComponentsMap {

    private static final ConcurrentMap<String, Object> components = new ConcurrentHashMap<>();

    private ComponentsMap() {}

    public static void add(final String key, final Object value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value must not be null");
        }

        if (components.containsKey(key)) {
            throw new IllegalArgumentException("Key already exists: " + key);
        }

        components.putIfAbsent(key, value);
    }

    public static ConcurrentMap<String, Object> getComponents() {
        return components;
    }
}
