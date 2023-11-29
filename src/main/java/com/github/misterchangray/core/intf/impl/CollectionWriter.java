package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.lang.reflect.Array;
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
            Object o = buffer.delayCalc(this.fieldMetaInfo.getDynamicRef().getId());
            count = (int) ConverterUtil.toNumber(this.fieldMetaInfo.getDynamicRef().getRealType(), (Number) o);
        }

        if(Objects.nonNull(val) && TypeEnum.ARRAY == this.fieldMetaInfo.getType()) {
            int length = Array.getLength(val);
            length = Math.min(length, count);

            for (int i = 0; i < length; i++) {
                count --;
                fieldMetaInfo.getGenericsField().getWriter().writeToBuffer(buffer, Array.get(val, i), val);
            }
        }


        if(Objects.nonNull(val) && TypeEnum.LIST == this.fieldMetaInfo.getType()) {
            List<?> list = (List<?>) val;

            int length = list.size();
            length = Math.min(length, count);
            for (int i = 0; i < length; i++) {
                count --;
                fieldMetaInfo.getGenericsField().getWriter().writeToBuffer(buffer,list.get(i), val);
            }
        }

        if(this.fieldMetaInfo.isDynamicSize()) {
            return;
        } else {
            for (int i = 0; i < count; i++) {
                fieldMetaInfo.getGenericsField().getWriter().writeToBuffer(buffer,null, val);
            }
        }

    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent, int writeOffset) throws IllegalAccessException {
        buffer.position(writeOffset);

        this.writeToBuffer(buffer, val, parent);
    }
}
