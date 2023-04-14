package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.util.ConverterUtil;

import java.util.Objects;

public class UByte {
    private byte abyte;

    public short get() {
        return abyte < 0 ?  ConverterUtil.byteToUnsigned(abyte) : abyte;
    }

    public void set(byte abyte) {
        this.abyte = abyte;
    }

    public UByte() {
    }
    public UByte(byte abyte) {
        this.abyte = abyte;
    }


    public static UByte valueOf(byte abyte) {
        return new UByte(abyte);
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
