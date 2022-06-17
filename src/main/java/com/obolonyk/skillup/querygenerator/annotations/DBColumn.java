package com.obolonyk.skillup.querygenerator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DBColumn {
    String name() default "";
    int number() default 0;
}
