package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.customconverter.customconverter.CustomArrayHandleAllConverter;

@MagicClass
public class CustomArrayHandleAll {
    @MagicField(order = 1)
    private int id;
    @MagicConverter(converter = CustomArrayHandleAllConverter.class, attachParams = {"1"}, handleCollection = true)
    @MagicField(order = 2)
    private Integer[] bookids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer[] getBookids() {
        return bookids;
    }

    public void setBookids(Integer[] bookids) {
        this.bookids = bookids;
    }
}
