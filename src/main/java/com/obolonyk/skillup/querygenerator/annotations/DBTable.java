package com.obolonyk.skillup.querygenerator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    String name() default "";
}
