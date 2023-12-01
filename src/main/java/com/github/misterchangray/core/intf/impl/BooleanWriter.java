package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:15
 **/
public class BooleanWriter extends MWriter {
    public BooleanWriter(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }

    @Override
    public void doWriteToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        if(Objects.isNull(val)) {
            val = (boolean) (this.fieldMetaInfo.getDefaultVal() > 0);
        }
        boolean tmp = (boolean) val;
        buffer.put((byte) (tmp ? 1 : 0));
    }


}
