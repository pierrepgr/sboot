package com.reinosoft.web;

import java.util.List;
import java.util.Optional;

public interface ComponentInstanceResolver {

    Optional<Object> getComponentInstance(String className);

    void createComponentInstance(List<String> classNames);
}
