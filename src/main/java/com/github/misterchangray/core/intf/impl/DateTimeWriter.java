package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.enums.TimestampFormatter;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DateUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.time.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2023-02-08 10:58
 **/
public class DateTimeWriter extends MWriter {
    public DateTimeWriter(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        int byteLen = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.getSize() <= 1) {
            byteLen = this.fieldMetaInfo.getElementBytes();
        }

        // direct write fill byte if the value is null
        byte[] data = new byte[byteLen];
        Arrays.fill(data, (byte) 0);
        if(!Objects.isNull(val)) {
            Class<?> clazz = this.fieldMetaInfo.getClazz();
            long timestamp = 0;
            if(clazz.isAssignableFrom(LocalTime.class)) {
                timestamp = ((LocalTime) val).toSecondOfDay();
            } else {
                if(clazz.isAssignableFrom(Date.class)) {
                    timestamp = ((Date) val).getTime();
                } else if(clazz.isAssignableFrom(Instant.class)) {
                    timestamp = ((Instant) val).toEpochMilli();
                } else if(clazz.isAssignableFrom(LocalDate.class)) {
                    timestamp = ((LocalDate) val).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                } else if (clazz.isAssignableFrom(LocalDateTime.class)) {
                    timestamp = ((LocalDateTime) val).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                }
                timestamp = DateUtil.timestampConvert(timestamp, TimestampFormatter.TO_TIMESTAMP_MILLIS, fieldMetaInfo.getTimestampFormatter());
            }
            byte[] res = ConverterUtil.numberToByte(timestamp);
            for (int i = data.length - 1, j=res.length - 1; i>=0 & j>=0;  i--, j--) {
                data[i] = res[j];
            }
        }

        buffer.put(data);
    }


}
