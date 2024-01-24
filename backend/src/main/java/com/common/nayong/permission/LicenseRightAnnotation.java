package com.common.nayong.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LicenseRightAnnotation {
    String displayKey() default "";
    RightType type() default RightType.Normal;
}
