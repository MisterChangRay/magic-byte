package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfoWrapper;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.util.Objects;

/**
 * @description: 数据写出接口，所有Writer父类
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
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        int position = buffer.position();
        this.doWriteToBuffer(buffer,val,parent);
        if(Objects.nonNull(this.fieldMetaInfo.getDynamicRef()) && !this.fieldMetaInfo.isDynamic()) {
            buffer.registerDelayWrapper(new FieldMetaInfoWrapper(this.fieldMetaInfo, position, null));
        }
    }

    public abstract void doWriteToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException;

    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent, int writeOffset) throws IllegalAccessException {};
}
