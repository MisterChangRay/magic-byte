package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:29
 **/
public class ByteObj {
    @MagicField(order = 1)
    private byte a;
    @MagicField(order = 2)
    private Byte b;

    public byte getA() {
        return a;
    }

    public void setA(byte a) {
        this.a = a;
    }

    public Byte getB() {
        return b;
    }

    public void setB(Byte b) {
        this.b = b;
    }
}
