package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfoWrapper;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * @description: 数据读取接口，所有Reader父类
 * @author: Ray.chang
 * @create: 2021-12-19 12:54
 **/
public abstract class MReader {
    protected FieldMetaInfo fieldMetaInfo;

    public abstract Object readFormObject(Object object) throws IllegalAccessException;

    public  Object  readFormBuffer(DynamicByteBuffer buffer, Object obj) throws IllegalAccessException {
        int position = buffer.position();
        Object o = this.doReadFormBuffer(buffer, obj);
        if(Objects.nonNull(this.fieldMetaInfo.getDynamicRef()) && !this.fieldMetaInfo.isDynamic()) {
            buffer.registerDelayWrapper(new FieldMetaInfoWrapper(this.fieldMetaInfo, position, o));
        }
        return o;
    }

    public  abstract Object  doReadFormBuffer(DynamicByteBuffer buffer, Object obj) throws IllegalAccessException;


    public MReader(FieldMetaInfo _fieldMetaInfo) {
        this.fieldMetaInfo = _fieldMetaInfo;
    }
}
