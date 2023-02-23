package com.github.misterchangray.core.common.converter.entity;


import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.common.converter.CustomSexEnumSerialize;

@MagicClass()
public class CustomConverterInField {
    @MagicField(order = 1)
    private int a;
    @MagicField(order = 2)
    @MagicConverter(converter = CustomSexEnumSerialize.class)
    private SexEnum b;
    @MagicField(order = 3)
    private int c;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public SexEnum getB() {
        return b;
    }

    public void setB(SexEnum b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
