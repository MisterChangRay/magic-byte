package com.github.misterchangray.core.common.entity.error;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 17:28
 **/
public class TestDynamicSizeOf {
    @MagicField(order = 1)
    private int a;
    @MagicField(order = 2, dynamicSizeOf = "a")
    private int b;

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
}
