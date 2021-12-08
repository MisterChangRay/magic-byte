package com.github.misterchangray.core.annotation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicField {
    /**
     * 序列号序号; 此序号使用后不能进行修改
     *
     * @return
     */
    int order();


    /**
     * 自动裁剪
     * @return
     */
    boolean autoTrim() default false;


    /**
     * 仅 string 和 list 需要显式指定字节大小
     *
     * 基本类型使用以下列表中的占用字节大小;
     * byte：8 bit
     * boolean：1 byte
     * char：1 byte
     * short：2 byte
     * int：4 byte
     * float：4 byte
     * double：8 byte
     * long：8 byte
     * string: 0 byte
     *
     * @return
     */
    int size() default 0;

    /**
     * take size from another field
     * @return
     */
    int dynamicSizeOf() default -1;


    /**
     * charset of String field
     * @return
     */
    String charset() default "ASCII";

    /**
     * use the fill bytes if the field is null
     * @return
     */
    byte fillByte() default (byte) 0xAE;
}