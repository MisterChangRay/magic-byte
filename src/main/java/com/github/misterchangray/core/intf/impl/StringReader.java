package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;

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
    public Object readFormBuffer(DynamicByteBuffer buffer) throws UnsupportedEncodingException {
        int byteLen = this.fieldMetaInfo.getSize();
        if(this.fieldMetaInfo.isDynamic()) {
            int position = buffer.position();
            buffer.position(this.fieldMetaInfo.getStartReadIndex());
            byteLen = (int) this.fieldMetaInfo.getDynamicRef().getReader().readFormBuffer(buffer);
            buffer.position(position);
        }

        byte[] tmp = new byte[byteLen];
        buffer.get(tmp);


        String res = new String(tmp, this.fieldMetaInfo.getCharset());
        if(this.fieldMetaInfo.isAutoTrim()) {
            res = res.trim();
        }
        return res;
    }

}
