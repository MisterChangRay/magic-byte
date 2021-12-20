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
    public void writeToBuffer(DynamicByteBuffer buffer, Object val) throws IllegalAccessException {
        int count = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            int position = buffer.position();
            buffer.position(this.fieldMetaInfo.getStartReadIndex());
            count = (int) this.fieldMetaInfo.getDynamicRef().getReader().readFormObject(val);
            buffer.position(position);
        }

        if(Objects.isNull(val)) {
            // direct write fill byte if the value is null
            byte[] fillBytes = new byte[count * this.fieldMetaInfo.getElementBytes()];
            buffer.put(fillBytes);
            return;
        }


        if(TypeEnum.ARRAY == this.fieldMetaInfo.getType()) {
            Object o = this.fieldMetaInfo.getReader().readFormObject(val);
            int length = Array.getLength(val);
            length = Math.min(length, count);

            for (int i = 0; i < length; i++) {
                fieldMetaInfo.getGenericsField().getWriter().writeToBuffer(buffer, Array.get(val, i));
            }
        }


        if(TypeEnum.LIST == this.fieldMetaInfo.getType()) {
            Object o = this.fieldMetaInfo.getReader().readFormObject(val);
            List<?> list = (List<?>) o;

            int length = list.size();
            length = Math.min(length, count);

            for (int i = 0; i < length; i++) {
                fieldMetaInfo.getGenericsField().getWriter().writeToBuffer(buffer,list.get(i));
            }
        }

    }


}
