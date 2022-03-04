package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:29
 **/
public class ShortObj {
    @MagicField(order = 1)
    private short a;
    @MagicField(order = 2)
    private Short b;

    public short getA() {
        return a;
    }

    public void setA(short a) {
        this.a = a;
    }

    public Short getB() {
        return b;
    }

    public void setB(Short b) {
        this.b = b;
    }
}
