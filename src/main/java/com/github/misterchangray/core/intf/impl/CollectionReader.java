package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.Packer;
import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.TypeManager;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.intf.MReader;
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
    public Object readFormBuffer(DynamicByteBuffer buffer) throws UnsupportedEncodingException {
        int count = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            int position = buffer.position();
            buffer.position(this.fieldMetaInfo.getStartReadIndex());
            count = (int) this.fieldMetaInfo.getDynamicRef().getReader().readFormBuffer(buffer);
            buffer.position(position);
        }

        if(TypeEnum.ARRAY == this.fieldMetaInfo.getType()) {
            Object array = Array.newInstance(fieldMetaInfo.getGenericsField().getClazz(), count);

            for(int i=0; i<count; i++) {
                Array.set(array, i, fieldMetaInfo.getGenericsField().getReader().readFormBuffer(buffer));
            }

            return array;
        }


        if(TypeEnum.LIST == this.fieldMetaInfo.getType()) {
            List<Object> list = new ArrayList<>(count);

            for(int i=0; i<count; i++) {
                list.add( fieldMetaInfo.getGenericsField().getReader().readFormBuffer(buffer));
            }

            return list;
        }

        return null;
    }

}
