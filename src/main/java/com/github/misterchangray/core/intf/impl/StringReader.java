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
    public Object doReadFormBuffer(DynamicByteBuffer buffer, Object entity) throws  IllegalAccessException {
        int byteLen = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            Object o = buffer.delayCalc(this.fieldMetaInfo.getDynamicRef().getAccessPath());
            byteLen = (int) ConverterUtil.toNumber(this.fieldMetaInfo.getDynamicRef().getType(), (Number) o);
        }

        int elementBytes = this.fieldMetaInfo.getOwnerClazz().getElementBytes();
        if(this.fieldMetaInfo.isDynamicSize() && buffer.capacity() < elementBytes) {
            int tmp =  elementBytes - buffer.capacity();
            tmp = this.fieldMetaInfo.getSize() - tmp;
            if(tmp >= 0) {
                byteLen = tmp;
            }
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
