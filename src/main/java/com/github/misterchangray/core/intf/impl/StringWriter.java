package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:15
 **/
public class StringWriter extends MWriter {
    public StringWriter(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        int byteLen = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            Object o = buffer.delayCalc(this.fieldMetaInfo.getDynamicRef().getId());
            byteLen = (int) ConverterUtil.toNumber(this.fieldMetaInfo.getDynamicRef().getType(), (Number) o);
        }
        // direct write fill byte if the value is null
        byte[] data = new byte[byteLen];
        Arrays.fill(data, (byte) 0);
        if(Objects.isNull(val)) val = "";

        byte[] tmp = val.toString().getBytes(fieldMetaInfo.getCharset());
        System.arraycopy(tmp, 0, data, 0, Math.min(tmp.length, byteLen));

        if(this.fieldMetaInfo.isDynamicSize()) {
            data = Arrays.copyOfRange(data, 0, tmp.length);
        }
        buffer.put(data);
    }


}
