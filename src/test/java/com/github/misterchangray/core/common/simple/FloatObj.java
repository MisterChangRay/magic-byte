package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:29
 **/
public class FloatObj {
    @MagicField(order = 1)
    private float a;
    @MagicField(order = 2)
    private Float b;

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public Float getB() {
        return b;
    }

    public void setB(Float b) {
        this.b = b;
    }
}
