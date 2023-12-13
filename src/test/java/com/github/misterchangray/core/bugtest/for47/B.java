package com.github.misterchangray.core.bugtest.for47;


import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.Arrays;
import java.util.Objects;

@MagicClass
public class B {

    @MagicField(order = 1)
    private A a;

    @MagicField(order = 2, dynamicSize = true, size = 10)
    private byte[] data;

    @MagicField(order = 3, size = 4)
    private byte[] check;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getCheck() {
        return check;
    }

    public void setCheck(byte[] check) {
        this.check = check;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        B b = (B) o;
        return Objects.equals(a, b.a) && Arrays.equals(data, b.data) && Arrays.equals(check, b.check);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(a);
        result = 31 * result + Arrays.hashCode(data);
        result = 31 * result + Arrays.hashCode(check);
        return result;
    }
}