package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.warpper.ULong;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2023-04-14 13:15
 **/
public class ULongWriter extends MWriter {
    public ULongWriter(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        super.writeToBuffer(buffer, val, parent);
        writeToBuffer(buffer, val, parent, buffer.position());

    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent, int writeOffset) throws IllegalAccessException {
        long data = (long) this.fieldMetaInfo.getDefaultVal();

        if(Objects.nonNull(val)) {
            data = ((ULong) val).signed();
        }
        buffer.putLong(writeOffset, data);
    }
}
