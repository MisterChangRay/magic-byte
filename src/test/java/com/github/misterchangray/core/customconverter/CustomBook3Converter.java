package com.github.misterchangray.core.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.customconverter.entity.Book;
import com.github.misterchangray.core.customconverter.entity.Book3;
import com.github.misterchangray.core.intf.MConverter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CustomBook3Converter implements MConverter<Book3> {
    @Override
    public MResult<Book3> pack(int nextReadIndex, byte[] fullBytes, String attachParams) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(fullBytes.length);
        byteBuffer.put(fullBytes);
        byteBuffer.position(nextReadIndex);
        Book3 book = new Book3();
        book.setId(byteBuffer.getInt());
        book.setCode(byteBuffer.getInt());


        return MResult.build(8 , book);

    }

    @Override
    public byte[] unpack(Book3 book3, String attachParams) {
        if(Objects.isNull(book3)) {
            return new byte[8];
        }
        ByteBuffer allocate = ByteBuffer.allocate(8 );
        allocate.putInt(book3.getId());
        allocate.putInt(book3.getCode());
        return allocate.array();

    }
}
