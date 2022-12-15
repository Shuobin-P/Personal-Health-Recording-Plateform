package com.google.personalhealthrecordingplateform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/13 14:48
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelProperty {
    String[] value() default {""};

    int index() default -1;
}
