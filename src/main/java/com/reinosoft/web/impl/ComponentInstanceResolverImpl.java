package com.reinosoft.web.impl;

import com.reinosoft.core.cache.ComponentsMap;
import com.reinosoft.exception.NewInstanceComponentException;
import com.reinosoft.utils.ClassUtils;
import com.reinosoft.web.ComponentInstanceResolver;
import com.reinosoft.web.annotations.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ComponentInstanceResolverImpl implements ComponentInstanceResolver {

    @Override
    public Optional<Object> getComponentInstance(String className) {
        return Optional.ofNullable(ComponentsMap.getComponents().get(className));
    }

    @Override
    public void createComponentInstance(List<String> classNames) {
        for (String className : classNames) {
            if (isComponentAnnotated(className)) {
                try {
                    Object instance = ClassUtils.getClass(className).getDeclaredConstructor().newInstance();
                    ComponentsMap.getComponents().putIfAbsent(className, instance);
                } catch (Exception e) {
                    throw new NewInstanceComponentException(String.format("Failed to create instance for class: %s", className), e);
                }
            }
        }
    }

    private boolean isComponentAnnotated(final String className) {
        return Arrays.stream(ClassUtils.getClass(className).getAnnotations())
                .map(Annotation::annotationType)
                .map(Class::getAnnotations)
                .flatMap(Stream::of)
                .anyMatch(annotation -> annotation.annotationType().equals(Component.class));
    }
}
