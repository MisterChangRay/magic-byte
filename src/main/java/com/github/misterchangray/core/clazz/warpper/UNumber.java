package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.util.ConverterUtil;

import java.math.BigInteger;
import java.util.Arrays;


public class UNumber {
    private byte[] adata;

    public byte[] getAdata() {
        return adata;
    }

    public void set(byte[] abytes) {
        this.adata = abytes;
    }

    public static UNumber valueOf(long anumber) {
        UNumber uNumber = new UNumber();
        uNumber.adata = ConverterUtil.numberToByte(anumber);
        return uNumber;
    }

    public static UNumber valueOf(BigInteger anumber) {
        UNumber uNumber = new UNumber();
        uNumber.adata = ConverterUtil.bigIntegerToByte(anumber);
        return uNumber;
    }

    public static UNumber valueOf(byte[] adata) {
        UNumber uNumber = new UNumber();
        uNumber.adata = adata;
        return uNumber;
    }

    public BigInteger get() {
        return ConverterUtil.byteToBigInteger(adata);
    }


    @Override
    public String toString() {
        return "UNumber{val=" + get() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UNumber uNumber = (UNumber) o;
        return Arrays.equals(adata, uNumber.adata);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(adata);
    }
}
