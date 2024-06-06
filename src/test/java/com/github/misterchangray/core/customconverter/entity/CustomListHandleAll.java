package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.customconverter.customconverter.CustomListHandleAllConverter;

import java.util.List;

@MagicClass
public class CustomListHandleAll {
    @MagicField(order = 1)
    private int id;
    @MagicConverter(converter = CustomListHandleAllConverter.class, attachParams = {"1"}, handleCollection = true)
    @MagicField(order = 2)
    private List<Integer> bookids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getBookids() {
        return bookids;
    }

    public void setBookids(List<Integer> bookids) {
        this.bookids = bookids;
    }
}
