package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.customconverter.entity.Book;
import com.github.misterchangray.core.intf.MConverter;

import java.util.Date;

public class CustomBookConverter implements MConverter<Book> {
    @Override
    public MResult<Book> pack(int nextReadIndex, byte[] fullBytes, String attachParams) {
        if(attachParams.equals("1")) {
            Book book = new Book();
            book.setCreateDate(new Date());
            book.setId(23);
            return MResult.build(0, book);


        } else {
            Book book2 = new Book();
            book2.setCreateDate(new Date());
            book2.setId(24);
            return MResult.build(0,book2);
        }

    }

    @Override
    public byte[] unpack(Book object, String attachParams) {
        return new byte[0];
    }
}
