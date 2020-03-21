package com.github.misterchangray.core.annotation;

import com.github.misterchangray.core.enums.ByteOrder;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicClass {
    ByteOrder byteOrder() default ByteOrder.LITTLE_ENDIAN;

    boolean enableHeader() default false;

    boolean enableValidator() default false;

    boolean enableAutoTrim() default false;

}