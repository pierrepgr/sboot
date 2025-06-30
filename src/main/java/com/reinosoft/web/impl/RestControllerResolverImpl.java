package com.reinosoft.web.impl;

import com.reinosoft.core.cache.RestControllersMap;
import com.reinosoft.web.RestControllerResolver;

import java.util.Optional;

public class RestControllerResolverImpl implements RestControllerResolver {

    @Override
    public Optional<RestControllersMap.RestControllerImpl> findControllerByKey(String key) {
        return Optional.ofNullable(RestControllersMap.getRestControllers().get(key.toLowerCase()));
    }
}
