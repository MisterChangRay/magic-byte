package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UNumber;
import com.github.misterchangray.core.custom.SimpleEnumConverter;

@MagicClass
public class EnumType {
    @MagicField(order = 1, size = 3)
    private UNumber a;
    @MagicConverter(converter = SimpleEnumConverter.class)
    @MagicField(order = 2, size = 1)
    private Book b;
    @MagicField(order = 3, size = 1)
    private UNumber c;

    public UNumber getC() {
        return c;
    }

    public void setC(UNumber c) {
        this.c = c;
    }

    public UNumber getA() {
        return a;
    }

    public void setA(UNumber a) {
        this.a = a;
    }

    public Book getB() {
        return b;
    }

    public void setB(Book b) {
        this.b = b;
    }
}
