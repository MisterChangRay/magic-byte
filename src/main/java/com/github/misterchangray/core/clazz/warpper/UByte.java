package com.github.misterchangray.core.clazz.warpper;

import java.util.Objects;

public class UByte {
    private byte abyte;

    public short get() {
        return abyte;
    }

    public void set(byte abyte) {
        this.abyte = abyte;
    }

    public UByte(byte abyte) {
        this.abyte = abyte;
    }

    @Override
    public String toString() {
        return "UByte{val=" + get() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UByte uByte = (UByte) o;
        return abyte == uByte.abyte;
    }

    @Override
    public int hashCode() {
        return Objects.hash(abyte);
    }
}
