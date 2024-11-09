package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.customconverter.entity.Book;
import com.github.misterchangray.core.customconverter.entity.Book2;
import com.github.misterchangray.core.customconverter.entity.IBook;
import com.github.misterchangray.core.intf.MConverter;

import java.util.Date;

public class CustomBookConverter implements MConverter<IBook> {
    @Override
    public MResult<IBook> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<IBook> clz, Object obj, Object root) {
        if(attachParams[0].equals("1")) {
            Book book = new Book();
            book.setCreateDate(new Date());
            book.setId(23);
            return MResult.build(0, book);

        }  else if(attachParams[0].equals("2")) {
            Book book2 = new Book();
            book2.setCreateDate(new Date());
            book2.setId(24);
            return MResult.build(0,book2);
        }else if(attachParams[0].equals("3")) {
            Book2 book2 = new Book2();
            book2.setCreateDate(new Date());
            book2.setId(25);
            return MResult.build(0,book2);
        }else if(attachParams[0].equals("4")) {
            Book2 book2 = new Book2();
            book2.setCreateDate(new Date());
            book2.setId(26);
            return MResult.build(0,book2);
        } else if(attachParams[0].equals("14")) {
            Book2 book2 = new Book2();
            book2.setCreateDate(new Date());
            book2.setId(26);
            return MResult.build(8,book2);
        }
        return MResult.build(0, null);
    }

    @Override
    public byte[] unpack(IBook object, String[] attachParams, Object rootObj) {
        if(attachParams[0].equals("14")) {

            return new byte[] {
                    0, 0, 0 ,1,
                    0, 0, 0 ,5
            };
        }
        return new byte[0];
    }
}
