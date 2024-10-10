package com.github.misterchangray.core.byteorder;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.ByteOrder;
import com.github.misterchangray.core.enums.TimestampFormatter;

import java.time.Instant;
import java.util.Objects;

@MagicClass
public class ByteOrderClass {

    @MagicField(order = 1)
    private int a;

    @MagicField(order = 2, byteOrder = ByteOrder.LITTLE_ENDIAN)
    private int b;

    @MagicField(order = 3, byteOrder = ByteOrder.LITTLE_ENDIAN, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS, size = 4)
    private Instant c;

    @MagicField(order = 4, byteOrder = ByteOrder.BIG_ENDIAN)
    private int d;

    @MagicField(order = 5, byteOrder = ByteOrder.BIG_ENDIAN, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS, size = 8)
    private Instant e;

    @MagicField(order = 6, byteOrder = ByteOrder.LITTLE_ENDIAN, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS, size = 6)
    private Instant f;

    @MagicField(order = 7, byteOrder = ByteOrder.LITTLE_ENDIAN, timestampFormat = TimestampFormatter.TO_TIMESTAMP_STRING, size = 14, formatPattern = "yyyyMMddHHmmss")
    private Instant g;

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

    public Instant getC() {
        return c;
    }

    public void setC(Instant c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public Instant getE() {
        return e;
    }

    public void setE(Instant e) {
        this.e = e;
    }

    public Instant getF() {
        return f;
    }

    public void setF(Instant f) {
        this.f = f;
    }

    public Instant getG() {
        return g;
    }

    public void setG(Instant g) {
        this.g = g;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteOrderClass that = (ByteOrderClass) o;
        return a == that.a && b == that.b && d == that.d && Objects.equals(c, that.c) && Objects.equals(e, that.e) && Objects.equals(f, that.f) && Objects.equals(g, that.g);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d, e, f, g);
    }
}
