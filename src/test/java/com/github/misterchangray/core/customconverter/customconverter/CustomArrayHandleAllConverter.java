package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayHandleAllConverter implements MConverter<Integer[]> {
    @Override
    public MResult<Integer[]> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class clz, Object fieldObj, Object rootObj) {
        Integer[] res = new Integer[3];
        res[0] = (Integer.valueOf( fullBytes[nextReadIndex]));
        res[1] = (Integer.valueOf( fullBytes[nextReadIndex + 1]));
        res[2] = (Integer.valueOf( fullBytes[nextReadIndex + 2]));


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
