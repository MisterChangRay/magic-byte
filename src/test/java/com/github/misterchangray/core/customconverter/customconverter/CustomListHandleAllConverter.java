package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;

import java.util.ArrayList;
import java.util.List;

public class CustomListHandleAllConverter  implements MConverter<List<Integer>> {
    @Override
    public MResult<List<Integer>> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<List<Integer>> clz, Object fieldObj, Object rootObj) {
        List<Integer> res = new ArrayList<>();
        res.add(Integer.valueOf( fullBytes[nextReadIndex]));
        res.add(Integer.valueOf( fullBytes[nextReadIndex + 1]));
        res.add(Integer.valueOf( fullBytes[nextReadIndex + 2]));


        return MResult.build(3, res);
    }

    @Override
    public byte[] unpack(List<Integer> object, String[] attachParams, Object rootObj) {
        byte[] bytes = new byte[object.size()];
        for (int i = 0; i < object.size(); i++) {
            bytes[i] = object.get(i).byteValue();
        }
        return bytes;
    }
}
