package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.Packer;
import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.TypeManager;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: read collection data
 * @author: Ray.chang
 * @create: 2021-12-20 13:15
 **/
public class CollectionReader extends MReader {
    public CollectionReader(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public Object readFormObject(Object object) throws IllegalAccessException {
        return this.fieldMetaInfo.getField().get(object);
    }

    @Override
    public Object readFormBuffer(DynamicByteBuffer buffer, Object entity) throws UnsupportedEncodingException, IllegalAccessException {
        int count = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            Object o = this.fieldMetaInfo.getDynamicRef().getReader().readFormObject(entity);
            count = (int) ConverterUtil.toNumber(this.fieldMetaInfo.getDynamicRef().getType(), o);
        }

        int elementBytes = this.fieldMetaInfo.getOwnerClazz().getElementBytes();
        if(this.fieldMetaInfo.isAutoTrim() && buffer.capacity() < elementBytes) {
            int tmp =  elementBytes - buffer.capacity();
            tmp = this.fieldMetaInfo.getSize() - (tmp / this.fieldMetaInfo.getElementBytes());
            if(tmp >= 0) {
                count = tmp;
            }
        }

        if(TypeEnum.ARRAY == this.fieldMetaInfo.getType()) {
            long allocSize = count * fieldMetaInfo.getGenericsField().getElementBytes();
            AssertUtil.throwIFOOM(allocSize, fieldMetaInfo.getGenericsField().getFullName());

            Object array = Array.newInstance(fieldMetaInfo.getGenericsField().getClazz(), count);

            for(int i=0; i<count; i++) {
                Object o = fieldMetaInfo.getGenericsField().getReader().readFormBuffer(buffer, entity);
                Array.set(array, i, o);
            }

            return array;
        }


        if(TypeEnum.LIST == this.fieldMetaInfo.getType()) {
            long allocSize = count * fieldMetaInfo.getGenericsField().getElementBytes();
            AssertUtil.throwIFOOM(allocSize, fieldMetaInfo.getGenericsField().getFullName());

            List<Object> list = new ArrayList<>(count);

            for(int i=0; i<count; i++) {
                list.add( fieldMetaInfo.getGenericsField().getReader().readFormBuffer(buffer, entity));
            }

            return list;
        }

        return null;
    }

}
