package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.enums.DateFormatEnum;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DateUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;
import java.time.*;
import java.util.Arrays;
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
    public Object readFormBuffer(DynamicByteBuffer buffer, Object entity) throws IllegalAccessException {
        int byteLen = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.getSize() <= 1) {
            byteLen = this.fieldMetaInfo.getElementBytes();
        }

        byte[] tmp = new byte[byteLen];
        buffer.get(tmp);
        long timestamp = ConverterUtil.byteToNumber(tmp);


        Class<?> clazz = this.fieldMetaInfo.getClazz();
        if(clazz.isAssignableFrom(LocalTime.class)) {
            return LocalTime.ofSecondOfDay(timestamp);
        }

        timestamp = DateUtil.timestampConvert(timestamp, fieldMetaInfo.getDateFormatEnum(), DateFormatEnum.TO_TIMESTAMP_MILLIS);
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
