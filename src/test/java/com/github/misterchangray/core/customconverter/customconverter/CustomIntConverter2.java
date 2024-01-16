package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.customconverter.entity.Book3;
import com.github.misterchangray.core.intf.MConverter;

import java.nio.ByteBuffer;
import java.util.Objects;

public class CustomIntConverter2 implements MConverter<Integer> {
    @Override
    public MResult<Integer> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class clz, Object staff14, Object root) {
        return MResult.build(4, 10);
    }

    @Override
    public byte[] unpack(Integer book3, String[] attachParams, Object rootObj) {

        return new byte[]{00,00,00,10};

    }
}
