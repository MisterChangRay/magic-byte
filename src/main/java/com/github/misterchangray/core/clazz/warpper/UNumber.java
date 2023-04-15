package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.util.ConverterUtil;

import java.math.BigInteger;
import java.util.Arrays;



public class UNumber {
    private byte[] adata;

    public void set(byte[] abytes) {
        this.adata = abytes;
    }

    public static UNumber valueOf(long anumber) {
        if(anumber < 0) {
            throw new MagicByteException("invalid anumber, this is unsigned number! try valueOf(BigInteger)");
        }

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

    /**
     *  get of unsigned
     * @return
     */
    public BigInteger get() {
        byte[] bytes = new byte[adata.length + 1];
        System.arraycopy(adata, 0, bytes, 1, adata.length);
        return new BigInteger(bytes);
    }


    /**
     * get of signed
     * @return
     */
    public byte[] raw() {
        return  adata;
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
        int max = Math.max(adata.length, uNumber.adata.length);
        byte b1, b2;
        for (int i = max - 1, j = (adata.length - 1), k = uNumber.adata.length - 1; i>=0  ; i--, j--, k--) {
            b1 = j < 0 ? 0 : adata[j];
            b2 = k < 0 ? 0 : uNumber.adata[k];
            if(b1 != b2) {
                return  false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(adata);
    }
}
