package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;

public class CustomIntConverter implements MConverter<Integer> {
    @Override
    public MResult<Integer> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<Integer> clz, Object staff14, Object root) {
        return MResult.build(4, 11);
    }

    @Override
    public byte[] unpack(Integer book3, String[] attachParams, Object rootObj) {

        return new byte[]{00,00,00,11};

    }
}
