package com.github.misterchangray.core.errorEntity;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 17:28
 **/
public class TestListMatrix {
    @MagicField(order = 1)
    private List<String> b;

    @MagicField(order = 2)
    private List<List<String>> a;

    public List<String> getB() {
        return b;
    }

    public void setB(List<String> b) {
        this.b = b;
    }

    public List<List<String>> getA() {
        return a;
    }

    public void setA(List<List<String>> a) {
        this.a = a;
    }
}
