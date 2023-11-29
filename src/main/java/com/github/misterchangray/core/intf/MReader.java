package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfoWrapper;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 12:54
 **/
public abstract class MReader {
    protected FieldMetaInfo fieldMetaInfo;

    public abstract Object readFormObject(Object object) throws IllegalAccessException;

    public abstract   Object  readFormBuffer(DynamicByteBuffer buffer, Object entity) throws IllegalAccessException;

    public void saveDelayCalcIfDynamic(DynamicByteBuffer buffer, Object entity) {
        if(Objects.nonNull(this.fieldMetaInfo.getDynamicRef())) {
            buffer.registerDelayWrapper(fieldMetaInfo.getId(),
                    new FieldMetaInfoWrapper(this.fieldMetaInfo, buffer.position()));
        }
    }

    public MReader(FieldMetaInfo _fieldMetaInfo) {
        this.fieldMetaInfo = _fieldMetaInfo;
    }
}
