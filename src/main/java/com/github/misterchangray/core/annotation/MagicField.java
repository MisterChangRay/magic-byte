package com.github.misterchangray.core.annotation;

import com.github.misterchangray.core.enums.TimestampFormatter;

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
     * 命令代码; 此代码配合消息委托使用
     * @return
     */
    boolean cmdField() default false;

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
    int size() default -1;

    /**
     * take size from another field id
     * @return
     */
    String dynamicSizeOf() default "";


    /**
     * charset of String field
     * @return
     */
    String charset() default "";


    /**
     * default value if the field is null
     * @return
     */
    int defaultVal() default -1;

    /**
     * 自动计算长度
     * @return
     */
    boolean calcLength() default false;

    /**
     * 自动计算校验和字段
     * @return
     */
    boolean calcCheckCode() default false;

    /**
     * 自动裁剪
     * 当整个数据只有一个可变数据项时可使用。因为其他数据项长度固定。所以可以反推出可变数据长度
     * 不建议使用此配置, 序列化很影响性能。
     * @return
     */
    boolean dynamicSize() default false;

    /**
     * 时间戳格式
     * 提供转为时间戳，可以将日期直接转换为时间戳
     *
     * @return
     */
    TimestampFormatter timestampFormat() default TimestampFormatter.TO_TIMESTAMP_MILLIS;

    /**
     * 日期格式化模式
     * @return
     */
    String formatPattern() default "yyyyMMddHHmmss";

}