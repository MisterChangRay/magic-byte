package com.github.misterchangray.core.annotation;


import com.github.misterchangray.core.enums.ByteOrder;

import java.lang.annotation.*;


@Documented
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicClass {

    /**
     * 大小端
     * @return
     */
    ByteOrder byteOrder() default ByteOrder.BIG_ENDIAN;

    /**
     * 严格模式
     * @return
     */
    boolean strict() default  false;

}