package com.github.misterchangray.core.util;


import com.github.misterchangray.core.enums.DateFormatEnum;

public class DateUtil {

    /**
     * 将一种时间戳格式转为另外一种格式
     * 比如将 秒级时间戳转为毫秒时间戳
     * @param timestamp
     * @param origin 原始时间戳格式
     * @param to 欲转换的格式
     * @return
     */

    public static long timestampConvert(long timestamp, DateFormatEnum origin, DateFormatEnum to ) {
        switch (origin) {
            case TO_TIMESTAMP_MINUTES:
                if(to == DateFormatEnum.TO_TIMESTAMP_HOURS) {
                    return timestamp / 60;
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_DAYS) {
                    return timestamp / (24 * 60);
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_SECONDS) {
                    return timestamp * 60;
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_MILLIS) {
                    return timestamp * 60 * 1000;
                }
                break;
            case TO_TIMESTAMP_DAYS:
                if(to == DateFormatEnum.TO_TIMESTAMP_HOURS) {
                    return timestamp * 24;
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_MINUTES) {
                    return timestamp * 24 * 60;
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_SECONDS) {
                    return timestamp * 24 * 60 * 60;
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_MILLIS) {
                    return timestamp * 24 * 60 * 60 * 1000;
                }
                break;
            case TO_TIMESTAMP_HOURS:
                if(to == DateFormatEnum.TO_TIMESTAMP_DAYS) {
                    return timestamp / 24;
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_MINUTES) {
                    return timestamp * 60;
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_SECONDS) {
                    return timestamp * 60 * 60;
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_MILLIS) {
                    return timestamp  * 60 * 60 * 1000;
                }
                break;
            case TO_TIMESTAMP_MILLIS:
                if(to == DateFormatEnum.TO_TIMESTAMP_MINUTES) {
                    return timestamp / (60 * 1000);
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_HOURS) {
                    return timestamp / ( 60 * 60 * 1000);
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_DAYS) {
                    return timestamp / (24 * 60 * 60 * 1000);
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_SECONDS) {
                    return timestamp / 1000;
                }

                break;
            case TO_TIMESTAMP_SECONDS:
                if(to == DateFormatEnum.TO_TIMESTAMP_DAYS) {
                    return timestamp / (24 * 60 * 60);
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_HOURS) {
                    return timestamp / (60 * 60);
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_MINUTES) {
                    return timestamp / ( 60);
                }
                if(to == DateFormatEnum.TO_TIMESTAMP_MILLIS) {
                    return timestamp * 1000;
                }
                break;
        }

        return timestamp;
    }

}
