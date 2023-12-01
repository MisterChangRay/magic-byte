package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.Packer;
import com.github.misterchangray.core.UnPacker;
import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:15
 **/
public class ObjectWriter extends MWriter {
    public ObjectWriter(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public void writeToObject(Object target, Object val) throws IllegalAccessException {
        this.fieldMetaInfo.getField().set(target, val);
    }

    @Override
    public void doWriteToBuffer(DynamicByteBuffer buffer, Object val, Object parent) throws IllegalAccessException {
        if(Objects.isNull(val)) {
            // direct write fill byte if the value is null
            byte[] data = new byte[this.fieldMetaInfo.getElementBytes()];
            Arrays.fill(data, (byte) 0);
            buffer.put(data);
            return;
        }

        UnPacker.getInstance().unpackObject(buffer, val, this.fieldMetaInfo.getClazzMetaInfo(), parent);
    }


}
