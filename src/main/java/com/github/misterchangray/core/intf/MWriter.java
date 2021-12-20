package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.util.DynamicByteBuffer;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 12:54
 **/
public abstract class MWriter {
    protected FieldMetaInfo fieldMetaInfo;

    public MWriter(FieldMetaInfo _fieldMetaInfo) {
        this.fieldMetaInfo = _fieldMetaInfo;
    }

    public abstract void writeToObject(Object target, Object val) throws IllegalAccessException;

    public abstract void writeToBuffer(DynamicByteBuffer buffer, Object val) throws IllegalAccessException;
}
