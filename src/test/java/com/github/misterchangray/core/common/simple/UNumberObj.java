package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UNumber;

import java.math.BigInteger;

@MagicClass
public class UNumberObj {
    @MagicField(order = 1, size = 3)
    private UNumber a;
    @MagicField(order = 2, size = 2)
    private UNumber b;
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

    public UNumber getB() {
        return b;
    }

    public void setB(UNumber b) {
        this.b = b;
    }
}
