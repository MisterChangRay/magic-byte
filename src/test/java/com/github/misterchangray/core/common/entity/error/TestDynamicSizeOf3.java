package com.github.misterchangray.core.common.entity.error;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 17:28
 **/
public class TestDynamicSizeOf3 {
    @MagicField(order = 1, size = 2)
    private long a;
    @MagicField(order = 2, dynamicSizeOfId = "3")
    private List<Integer> b;
    @MagicField(order = 6)
    private long c;

    public long getC() {
        return c;
    }

    public void setC(long c) {
        this.c = c;
    }

    public long getA() {
        return a;
    }

    public void setA(long a) {
        this.a = a;
    }

    public List<Integer> getB() {
        return b;
    }

    public void setB(List<Integer> b) {
        this.b = b;
    }
}
