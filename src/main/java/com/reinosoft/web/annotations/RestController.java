package com.reinosoft.web.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Component
@Target(TYPE)
@Retention(RUNTIME)
public @interface RestController {
}
