package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.customconverter.entity.Book3;
import com.github.misterchangray.core.intf.MConverter;

import java.nio.ByteBuffer;
import java.util.Objects;

public class CustomConverterDynamicSize implements MConverter<Book3> {
    @Override
    public MResult<Book3> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class clz, Object book3, Object root) {

        Book3 book31 = new Book3();
        book31.setId(1111);
        book31.setCode(2222);

        return MResult.build(8 , book31);

    }

    @Override
    public byte[] unpack(Book3 book3, String[] attachParams) {
        return new byte[]{0x4,0x5,0x8,0x9, 0x6,0x7,0x8,0x10};

    }
}
