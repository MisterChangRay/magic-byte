package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.math.BigInteger;

@MagicClass
public class BigIntegerObj {
    @MagicField(order = 1, size = 3)
    private BigInteger a;
    @MagicField(order = 2, size = 2)
    private BigInteger b;

    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public BigInteger getB() {
        return b;
    }

    public void setB(BigInteger b) {
        this.b = b;
    }
}
