package com.github.misterchangray.core.common.converter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.common.converter.CustomSerialize;

@MagicConverter(converter = CustomSerialize.class)
@MagicClass()
public class CustomObj {
    private int a;
    private Character b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public Character getB() {
        return b;
    }

    public void setB(Character b) {
        this.b = b;
    }


}

