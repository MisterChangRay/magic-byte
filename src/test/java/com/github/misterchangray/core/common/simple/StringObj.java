package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:29
 **/
@MagicClass()
public class StringObj {
    @MagicField(order = 1, size = 5)
    private String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
