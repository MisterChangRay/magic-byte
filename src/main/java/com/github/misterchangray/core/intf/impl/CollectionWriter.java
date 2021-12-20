package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @description: write collection data
 * @author: Ray.chang
 * @create: 2021-12-20 13:15
 **/
public class CollectionWriter extends MWriter {
    public CollectionWriter(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        int count = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            int position = buffer.position();
            buffer.position(this.fieldMetaInfo.getStartReadIndex());
            count = (int) this.fieldMetaInfo.getDynamicRef().getReader().readFormObject(parent);
            buffer.position(position);
        }

        DynamicByteBuffer dynamicByteBuffer = DynamicByteBuffer.allocate(count * this.fieldMetaInfo.getElementBytes());
        dynamicByteBuffer.fill(this.fieldMetaInfo.getFillByte());

        if(TypeEnum.ARRAY == this.fieldMetaInfo.getType()) {
            int length = Array.getLength(val);
            length = Math.min(length, count);

            for (int i = 0; i < length; i++) {
                fieldMetaInfo.getGenericsField().getWriter().writeToBuffer(dynamicByteBuffer, Array.get(val, i), val);
            }
        }


        if(TypeEnum.LIST == this.fieldMetaInfo.getType()) {
            List<?> list = (List<?>) val;

            int length = list.size();
            length = Math.min(length, count);

            for (int i = 0; i < length; i++) {
                fieldMetaInfo.getGenericsField().getWriter().writeToBuffer(dynamicByteBuffer,list.get(i), val);
            }
        }

        buffer.put(dynamicByteBuffer.array());
    }


}
