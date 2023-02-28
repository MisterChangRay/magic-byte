package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.customconverter.customconverter.CustomStaff7Converter;

@MagicConverter(converter = CustomStaff7Converter.class, fixSize = 8)
@MagicClass
public class Staff7 {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2)
    private int length;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
