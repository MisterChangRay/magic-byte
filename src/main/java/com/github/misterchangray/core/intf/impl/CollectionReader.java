package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.ExceptionUtil;
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
    public Object doReadFormBuffer(DynamicByteBuffer buffer, Object obj) throws  IllegalAccessException {
        int count = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            Object o = buffer.delayCalc(this.fieldMetaInfo.getDynamicRef());
            count = (int) ConverterUtil.toNumber(this.fieldMetaInfo.getDynamicRef().getType(), (Number) o);
        }

        if(this.fieldMetaInfo.isDynamicSize()) {
            // 获取总字节数
            int elementBytes = this.fieldMetaInfo.getClazzMetaInfo().getRoot().getElementBytes();
            // 后续字节数 = 定义总字节数 - 已读字节数 - 填充最大字节数
            int suffixBytes = elementBytes - (buffer.position() + (this.fieldMetaInfo.getSize() * this.fieldMetaInfo.getElementBytes()));
            // 填充字节数 = 传输总字节数 - 已读字节数 - 后续字节数
            int fillBytes = buffer.capacity() - buffer.position() - suffixBytes;

            if(fillBytes >= 0) {
                count = fillBytes / this.fieldMetaInfo.getElementBytes();
            }
        }

        if(TypeEnum.ARRAY == this.fieldMetaInfo.getType()) {
            long allocSize = count * fieldMetaInfo.getGenericsField().getElementBytes();
            ExceptionUtil.throwIFOOM(allocSize, fieldMetaInfo.getGenericsField().getFullName());

            Object array = Array.newInstance(fieldMetaInfo.getGenericsField().getClazz(), count);

            for(int i=0; i<count; i++) {
                Object o = fieldMetaInfo.getGenericsField().getReader().readFormBuffer(buffer, obj);
                Array.set(array, i, o);
            }

            return array;
        }


        if(TypeEnum.LIST == this.fieldMetaInfo.getType()) {
            long allocSize = count * fieldMetaInfo.getGenericsField().getElementBytes();
            ExceptionUtil.throwIFOOM(allocSize, fieldMetaInfo.getGenericsField().getFullName());

            List<Object> list = new ArrayList<>(count);

            for(int i=0; i<count; i++) {
                list.add( fieldMetaInfo.getGenericsField().getReader().readFormBuffer(buffer, obj));
            }

            return list;
        }

        return null;
    }

}
