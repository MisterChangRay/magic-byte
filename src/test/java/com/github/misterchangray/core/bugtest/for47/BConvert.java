package com.github.misterchangray.core.bugtest.for47;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public      class BConvert implements MConverter<String> {
    public BConvert() {
    }

    @Override
    public MResult<String> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<String> clz, Object obj, Object root) {
        A a = (A) obj;
        if (a.getId() == 1) {
            return MResult.build(1,  new String(Arrays.copyOfRange(fullBytes, nextReadIndex, nextReadIndex + 1)));
        } else {
            return MResult.build(2, new String(Arrays.copyOfRange(fullBytes, nextReadIndex, nextReadIndex + 2)));
        }
    }

    @Override
    public byte[] unpack(String object, String[] attachParams, Object rootObj) {
        return object.getBytes(StandardCharsets.UTF_8);
    }
}