package com.github.misterchangray.core.entity.custom;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-22 12:19
 **/
public class OrderConfuse {
    @MagicField(order = 5)
    private int a;

    @MagicField(order = 2)
    private int b;

    @MagicField(order = 4)
    private int c;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
