package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfoWrapper;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.util.Objects;

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

    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }
    public  void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        if(Objects.nonNull(this.fieldMetaInfo.getDynamicRef())) {
            buffer.registerDelayWrapper(fieldMetaInfo.getId(),
                    new FieldMetaInfoWrapper(this.fieldMetaInfo, buffer.position()));
        }
    }

    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent, int writeOffset) throws IllegalAccessException {};
}
