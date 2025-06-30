package com.reinosoft.web;

import com.reinosoft.core.cache.RestControllersMap.RestControllerImpl;

import java.util.Optional;

public interface RestControllerResolver {

    Optional<RestControllerImpl> findControllerByKey(String key);
}
