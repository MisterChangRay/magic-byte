package com.github.misterchangray.core.byteorder;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.ByteOrder;

import java.util.Objects;

@MagicClass
public class ByteOrderClass {

    @MagicField(order = 1)
    private int a;

    @MagicField(order = 2, byteOrder = ByteOrder.LITTLE_ENDIAN)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteOrderClass that = (ByteOrderClass) o;
        return a == that.a && b == that.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
