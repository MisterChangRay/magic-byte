package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.CustomConverterInfo;
import com.github.misterchangray.core.clazz.TypeManager;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.util.Arrays;
import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2023-02-08 10:58
 **/
public class CustomWriter extends MWriter {
    public CustomWriter(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }

    @Override
    public void writeToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {

        int byteLen = Integer.MAX_VALUE;
        CustomConverterInfo customConverter = TypeManager.getCustomConverter(fieldMetaInfo.getClazz());
        if(Objects.nonNull(customConverter) && customConverter.isFixsize()) {
            byteLen = customConverter.getFixSize();
        }

        byte[] unpack = customConverter.getConverter().unpack(val, customConverter.getAttachParams());
        byteLen = Math.min(byteLen, unpack.length);

        // direct write fill byte if the value is null
        byte[] data = new byte[byteLen];
        Arrays.fill(data, (byte) 0);
        if(Objects.nonNull(unpack)) {
            data = Arrays.copyOf(unpack, byteLen);
        }

        buffer.put(data);
    }


}
