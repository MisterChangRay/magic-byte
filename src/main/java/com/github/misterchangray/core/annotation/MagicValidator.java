package com.github.misterchangray.core.annotation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicValidator {
}