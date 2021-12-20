package com.github.misterchangray.core.simple;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:29
 **/
public class LongObj {
    @MagicField(order = 1)
    private long a;
    @MagicField(order = 2)
    private Long b;

    public long getA() {
        return a;
    }

    public void setA(long a) {
        this.a = a;
    }

    public Long getB() {
        return b;
    }

    public void setB(Long b) {
        this.b = b;
    }
}
