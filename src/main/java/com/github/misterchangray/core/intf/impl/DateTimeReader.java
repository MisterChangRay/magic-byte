package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.enums.TimestampFormatter;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DateUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Date;

/**
 * @description: read date data
 * @author: Ray.chang
 * @create: 2023-02-08 10:58
 **/
public class DateTimeReader extends MReader {
    public DateTimeReader(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public Object readFormObject(Object object) throws IllegalAccessException {
        return this.fieldMetaInfo.getField().get(object);
    }

    @Override
    public Object doReadFormBuffer(DynamicByteBuffer buffer, Object entity) throws IllegalAccessException {
        int byteLen = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.getSize() <= 1) {
            byteLen = this.fieldMetaInfo.getElementBytes();
        }

        byte[] tmp = new byte[byteLen];
        buffer.get(tmp);
        Class<?> clazz = this.fieldMetaInfo.getClazz();

        long timestamp = 0;
        if(this.fieldMetaInfo.getTimestampFormatter() == TimestampFormatter.TO_TIMESTAMP_STRING) {
            String dateStr = new String(tmp, StandardCharsets.UTF_8);
            if(clazz.isAssignableFrom(LocalTime.class)) {
                timestamp = DateUtil.txtToLocalTime(dateStr, this.fieldMetaInfo.getFormatPattern()).toSecondOfDay();
            } else {
                timestamp = DateUtil.stringToDate(dateStr, this.fieldMetaInfo.getFormatPattern()).getTime();

            }
        } else {
            timestamp = ConverterUtil.byteToNumber(tmp, buffer.getOrder());
        }



        if(clazz.isAssignableFrom(LocalTime.class)) {
            return LocalTime.ofSecondOfDay(timestamp);
        }

        timestamp = DateUtil.timestampConvert(timestamp, fieldMetaInfo.getTimestampFormatter(), TimestampFormatter.TO_TIMESTAMP_MILLIS);
        Date date = new Date(timestamp);
        if(clazz.isAssignableFrom(Date.class)) {
            return date;
        } else if(clazz.isAssignableFrom(Instant.class)) {
            return date.toInstant();
        } else if(clazz.isAssignableFrom(LocalDate.class)) {
            return LocalDate.from(date.toInstant().atZone(ZoneId.systemDefault()));
        } else if (clazz.isAssignableFrom(LocalDateTime.class)) {
            return LocalDateTime.from(date.toInstant().atZone(ZoneId.systemDefault()));
        }
        return null;
    }

}
