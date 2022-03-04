package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:29
 **/
public class DoubleObj {
    @MagicField(order = 1)
    private double a;
    @MagicField(order = 2)
    private Double b;

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }
}
