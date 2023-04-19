package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.warpper.UShort;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.DynamicByteBuffer;

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
        short data = (short) this.fieldMetaInfo.getDefaultVal();

        if(Objects.nonNull(val)) {
            data = ((UShort) val).signed();
        }
        buffer.putShort(data);
    }
}
