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
}