package com.github.misterchangray.core.enums;

/**
 * 日期格式化枚举类
 */
public enum TimestampFormatter {
    /**
     * 转为毫秒时间戳
     */
    TO_TIMESTAMP_MILLIS(),
    /**
     * 转为分钟时间戳
     */
    TO_TIMESTAMP_MINUTES(),
    /**
     * 转为秒时间戳
     */
    TO_TIMESTAMP_SECONDS(),
    /**
     * 转为小时时间戳
     */
    TO_TIMESTAMP_HOURS(),
    /**
     * 转为天时间戳
     */
    TO_TIMESTAMP_DAYS(),
    /**
     * 转为字符串, 使用format进行格式化输出
     */

    TO_TIMESTAMP_STRING()
    ;




    /**
     */
    TimestampFormatter() {
    }

}
