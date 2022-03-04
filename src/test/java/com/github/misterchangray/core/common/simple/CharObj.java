package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-19 13:29
 **/
public class CharObj {
    @MagicField(order = 1)
    private char a;
    @MagicField(order = 2)
    private Character b;


    public char getA() {
        return a;
    }

    public void setA(char a) {
        this.a = a;
    }

    public Character getB() {
        return b;
    }

    public void setB(Character b) {
        this.b = b;
    }
}
