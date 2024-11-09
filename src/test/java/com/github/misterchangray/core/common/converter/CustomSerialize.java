package com.github.misterchangray.core.common.converter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.common.converter.entity.CustomObj;
import com.github.misterchangray.core.intf.MConverter;

public class CustomSerialize implements MConverter<CustomObj> {

    @Override
    public MResult<CustomObj> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<CustomObj> clz, Object tmp, Object root) {
        CustomObj customObj = new CustomObj();
        customObj.setA(fullBytes[0]);
        customObj.setB((char)fullBytes[1]);
        return MResult.build(2, customObj);
    }

    @Override
    public byte[] unpack(CustomObj object, String[] attachParams, Object rootObj) {
        return new byte[]{(byte)object.getA(), (byte)object.getB().charValue()};
    }
}