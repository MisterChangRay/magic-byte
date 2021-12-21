package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @description: read collection data
 * @author: Ray.chang
 * @create: 2021-12-20 13:15
 **/
public class StringReader extends MReader {
    public StringReader(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public Object readFormObject(Object object) throws IllegalAccessException {
        return this.fieldMetaInfo.getField().get(object);
    }

    @Override
    public Object readFormBuffer(DynamicByteBuffer buffer, Object entity) throws UnsupportedEncodingException, IllegalAccessException {
        int byteLen = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            Object o = this.fieldMetaInfo.getDynamicRef().getReader().readFormObject(entity);
            byteLen = (int) ConverterUtil.toNumber(this.fieldMetaInfo.getDynamicRef().getType(), o);
        }


        byte[] tmp = new byte[byteLen];
        int actualLen = tmp.length;

        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = buffer.get();
            if(tmp[i] == 0) {
                actualLen --;
            }
        }

        return new String(Arrays.copyOfRange(tmp, 0, actualLen) , this.fieldMetaInfo.getCharset());
    }

}
