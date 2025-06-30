package com.reinosoft.web;

import java.util.Optional;

public interface RestControllerInstanceResolver {

    Optional<Object> getRestControllerInstance(String className);
}
