package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.Packer;
import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;

/**
 * @description: read collection data
 * @author: Ray.chang
 * @create: 2021-12-20 13:15
 **/
public class ObjectReader extends MReader {
    public ObjectReader(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public Object readFormObject(Object object) throws IllegalAccessException {
        return this.fieldMetaInfo.getField().get(object);
    }

    @Override
    public Object readFormBuffer(DynamicByteBuffer buffer) throws UnsupportedEncodingException {
        return Packer.getInstance().packObject(buffer, this.fieldMetaInfo.getClazz());
    }

}
