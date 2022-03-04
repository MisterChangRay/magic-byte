package com.github.misterchangray.core.common.entity.error;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 17:28
 **/
public class TestLargeString {
    @MagicField(order = 1, size = Integer.MAX_VALUE)
    private String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
