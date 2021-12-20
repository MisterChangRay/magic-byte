package com.github.misterchangray.core.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:29
 **/
@MagicClass(autoTrim = true)
public class ColletionObj {
    @MagicField(order = 1, size = 5)
    private byte[] a;

    @MagicField(order = 2, size = 5)
    private Integer[] b;

    @MagicField(order = 3, size = 5)
    private List<Long> c;

    public byte[] getA() {
        return a;
    }

    public void setA(byte[] a) {
        this.a = a;
    }

    public Integer[] getB() {
        return b;
    }

    public void setB(Integer[] b) {
        this.b = b;
    }

    public List<Long> getC() {
        return c;
    }

    public void setC(List<Long> c) {
        this.c = c;
    }
}
