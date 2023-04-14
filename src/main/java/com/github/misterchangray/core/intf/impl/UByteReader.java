package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.warpper.UNumber;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.DynamicByteBuffer;

/**
 * @description:
 * @author: Ray.chang
 * @create:  2023-04-14 13:15
 **/
public class UByteReader extends MReader {
    public UByteReader(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public Object readFormObject(Object object) throws IllegalAccessException {
        return this.fieldMetaInfo.getField().get(object);
    }

    @Override
    public Object readFormBuffer(DynamicByteBuffer buffer, Object entity) {
        byte[] tmp = new byte[this.fieldMetaInfo.getElementBytes()];

        return UNumber.valueOf(tmp);
    }
}
