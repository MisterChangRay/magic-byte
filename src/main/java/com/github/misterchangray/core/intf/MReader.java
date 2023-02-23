package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 12:54
 **/
public abstract class MReader {
    protected FieldMetaInfo fieldMetaInfo;

    public abstract Object readFormObject(Object object) throws IllegalAccessException;

    public abstract Object  readFormBuffer(DynamicByteBuffer buffer, Object entity) throws IllegalAccessException;

    public MReader(FieldMetaInfo _fieldMetaInfo) {
        this.fieldMetaInfo = _fieldMetaInfo;
    }
}
