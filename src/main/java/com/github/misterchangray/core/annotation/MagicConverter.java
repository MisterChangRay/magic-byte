package com.github.misterchangray.core.annotation;


import com.github.misterchangray.core.intf.MConverter;

import java.lang.annotation.*;

/**
 * 自定义序列化接口类
 */
@Documented
@Target({ ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicConverter {


    /**
     * 附加参数
     * @return
     */
    String[] attachParams() default "";

    /**
     * 转换器
     * @return
     */
    Class<? extends MConverter> converter() ;


    /**
     * 固定长度
     * @return
     */
    int fixSize() default -1;

    /**
     * 集合类序列化控制
     *
     * 默认只需要实现单个元素的序列化过程即可
     * 当此字段为true时, 则需要需要实现整个集合的序列化过程
     *
     * @return
     */
    boolean handleCollection() default false;
}