package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.warpper.UNumber;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.util.Arrays;
import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2023-04-14 13:15
 **/
public class UShortWriter extends MWriter {
    public UShortWriter(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        writeToBuffer(buffer, val, parent, buffer.position());

    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent, int writeOffset) throws IllegalAccessException {
        if(Objects.isNull(val)) {
            val = UNumber.valueOf( this.fieldMetaInfo.getDefaultVal());
        }
        byte[] data = new byte[fieldMetaInfo.getSize()];
        Arrays.fill(data, (byte) 0);

        byte[] res = ((UNumber) val).getAdata();
        for (int i = data.length - 1, j=res.length - 1; i>=0 & j>=0;  i--, j--) {
            data[i] = res[j];
        }
        buffer.put(data);
    }
}
