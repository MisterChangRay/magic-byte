package com.github.misterchangray.core.util;


import com.github.misterchangray.core.enums.TimestampFormatter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 注意ISO 8601规定的日期和时间分隔符是T。标准格式如下：
 *
 * 日期：yyyy-MM-dd
 * 时间：HH:mm:ss
 * 带毫秒的时间：HH:mm:ss.SSS
 * 日期和时间：yyyy-MM-dd'T'HH:mm:ss
 * 带毫秒的日期和时间：yyyy-MM-dd'T'HH:mm:ss.SSS
 */
public class DateUtil {
    static Map<String, DateTimeFormatter> dateTimeFormatterMap = new HashMap<>();

    /**
     * 字符串格式的日期转换
     * @param date
     * @param datePattern
     * @return
     */
    public static String dateToString(Date date, String datePattern) {
        DateTimeFormatter dateTimeFormatter = null;
        if(Objects.isNull(dateTimeFormatter = dateTimeFormatterMap.get(datePattern))) {
            dateTimeFormatter =  DateTimeFormatter.ofPattern(datePattern).withZone(ZoneId.systemDefault());
            dateTimeFormatterMap.put(datePattern, dateTimeFormatter);
        }

        return dateTimeFormatter.format(date.toInstant());
    }

    public static Date stringToDate(String date, String datePattern) {
        DateTimeFormatter dateTimeFormatter = null;
        if(Objects.isNull(dateTimeFormatter = dateTimeFormatterMap.get(datePattern))) {
            dateTimeFormatter =  DateTimeFormatter.ofPattern(datePattern).withZone(ZoneId.systemDefault());
            dateTimeFormatterMap.put(datePattern, dateTimeFormatter);
        }

        TemporalAccessor parse = dateTimeFormatter.parse(date);
        int i1=0,i2=0,i3=0,i4=0,i5=0,i6=0,i7=0,i8=0;
        try {
            i1 = parse.get(ChronoField.YEAR);
            i2 = parse.get(ChronoField.MONTH_OF_YEAR);
            i3 = parse.get(ChronoField.DAY_OF_MONTH);
            i4 = parse.get(ChronoField.HOUR_OF_DAY);
            i5 = parse.get(ChronoField.MINUTE_OF_HOUR);
            i6 = parse.get(ChronoField.SECOND_OF_MINUTE);
            i7 = parse.get(ChronoField.NANO_OF_SECOND);
        } catch (DateTimeException ae) {}

        LocalDateTime of = LocalDateTime.of(i1, i2, i3, i4, i5, i6, i7);
        return new Date(Instant.from(of.atZone(ZoneId.systemDefault())).toEpochMilli());
    }

    /**
     * 将一种时间戳格式转为另外一种格式
     * 比如将 秒级时间戳转为毫秒时间戳
     * @param timestamp
     * @param origin 原始时间戳格式
     * @param to 欲转换的格式
     * @return
     */

    public static long timestampConvert(long timestamp, TimestampFormatter origin, TimestampFormatter to ) {
        switch (origin) {
            case TO_TIMESTAMP_MINUTES:
                if(to == TimestampFormatter.TO_TIMESTAMP_HOURS) {
                    return timestamp / 60;
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_DAYS) {
                    return timestamp / (24 * 60);
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_SECONDS) {
                    return timestamp * 60;
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_MILLIS) {
                    return timestamp * 60 * 1000;
                }
                break;
            case TO_TIMESTAMP_DAYS:
                if(to == TimestampFormatter.TO_TIMESTAMP_HOURS) {
                    return timestamp * 24;
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_MINUTES) {
                    return timestamp * 24 * 60;
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_SECONDS) {
                    return timestamp * 24 * 60 * 60;
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_MILLIS) {
                    return timestamp * 24 * 60 * 60 * 1000;
                }
                break;
            case TO_TIMESTAMP_HOURS:
                if(to == TimestampFormatter.TO_TIMESTAMP_DAYS) {
                    return timestamp / 24;
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_MINUTES) {
                    return timestamp * 60;
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_SECONDS) {
                    return timestamp * 60 * 60;
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_MILLIS) {
                    return timestamp  * 60 * 60 * 1000;
                }
                break;
            case TO_TIMESTAMP_MILLIS:
                if(to == TimestampFormatter.TO_TIMESTAMP_MINUTES) {
                    return timestamp / (60 * 1000);
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_HOURS) {
                    return timestamp / ( 60 * 60 * 1000);
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_DAYS) {
                    return timestamp / (24 * 60 * 60 * 1000);
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_SECONDS) {
                    return timestamp / 1000;
                }

                break;
            case TO_TIMESTAMP_SECONDS:
                if(to == TimestampFormatter.TO_TIMESTAMP_DAYS) {
                    return timestamp / (24 * 60 * 60);
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_HOURS) {
                    return timestamp / (60 * 60);
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_MINUTES) {
                    return timestamp / ( 60);
                }
                if(to == TimestampFormatter.TO_TIMESTAMP_MILLIS) {
                    return timestamp * 1000;
                }
                break;
        }

        return timestamp;
    }

    public static LocalTime txtToLocalTime(String dateStr, String formatPattern) {
        DateTimeFormatter dateTimeFormatter = null;
        if(Objects.isNull(dateTimeFormatter = dateTimeFormatterMap.get(formatPattern))) {
            dateTimeFormatter =  DateTimeFormatter.ofPattern(formatPattern).withZone(ZoneId.systemDefault());
            dateTimeFormatterMap.put(formatPattern, dateTimeFormatter);
        }

        LocalTime t = LocalTime.parse(dateStr, dateTimeFormatter);
        return t;
    }


    public static String localTimeToTxt(long secondOfDay, String formatPattern) {
        DateTimeFormatter dateTimeFormatter = null;
        if(Objects.isNull(dateTimeFormatter = dateTimeFormatterMap.get(formatPattern))) {
            dateTimeFormatter =  DateTimeFormatter.ofPattern(formatPattern).withZone(ZoneId.systemDefault());
            dateTimeFormatterMap.put(formatPattern, dateTimeFormatter);
        }

        LocalTime localTime = LocalTime.ofSecondOfDay(secondOfDay);
        return dateTimeFormatter.format(localTime);

    }
}
