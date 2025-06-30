package com.reinosoft.web.impl;

import com.reinosoft.core.cache.ComponentsMap;
import com.reinosoft.web.RestControllerInstanceResolver;

import java.util.Optional;

public class RestControllerInstanceResolverImpl implements RestControllerInstanceResolver {

    @Override
    public Optional<Object> getRestControllerInstance(String className) {
        return Optional.ofNullable(ComponentsMap.getComponents().get(className));
    }
}
