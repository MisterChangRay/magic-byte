package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;

public class CustomArrayHandleAllConverter implements MConverter<Integer[]> {
    @Override
    public MResult<Integer[]> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<Integer[]> clz, Object fieldObj, Object rootObj) {
        Integer[] res = new Integer[3];
        res[0] = ((int) fullBytes[nextReadIndex]);
        res[1] = ((int) fullBytes[nextReadIndex + 1]);
        res[2] = ((int) fullBytes[nextReadIndex + 2]);


        return MResult.build(3, res);
    }

    @Override
    public byte[] unpack(Integer[] object, String[] attachParams, Object rootObj) {
        byte[] bytes = new byte[object.length];
        for (int i = 0; i < object.length; i++) {
            bytes[i] = object[i].byteValue();
        }
        return bytes;
    }
}
