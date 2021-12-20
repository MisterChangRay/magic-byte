package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.intf.MWriter;
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
    public void writeToBuffer(DynamicByteBuffer buffer, Object val) throws IllegalAccessException {
        int byteLen = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            int position = buffer.position();
            buffer.position(this.fieldMetaInfo.getStartReadIndex());
            byteLen = (int) this.fieldMetaInfo.getDynamicRef().getReader().readFormObject(val);
            buffer.position(position);
        }
        // direct write fill byte if the value is null
        byte[] data = new byte[byteLen];
        Arrays.fill(data, this.fieldMetaInfo.getFillByte());
        if(Objects.isNull(val)) val = "";

        byte[] tmp = val.toString().getBytes(Charset.forName(fieldMetaInfo.getCharset()));
        System.arraycopy(tmp, 0, data, 0, Math.min(tmp.length, byteLen));
        buffer.put(data);
    }


}
