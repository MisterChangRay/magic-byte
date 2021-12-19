package com.github.misterchangray.core.errorEntity;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 17:28
 **/
public class TestMatrix {
    @MagicField(order = 1)
    private List<String> b;

    @MagicField(order = 2)
    private List<List<String>> a;


    @MagicField(order = 3)
    private int c;
    @MagicField(order = 4)
    private int[] d;

    private int[][] e;

    public int[] getD() {
        return d;
    }

    public void setD(int[] d) {
        this.d = d;
    }

    public int[][] getE() {
        return e;
    }

    public void setE(int[][] e) {
        this.e = e;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

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
