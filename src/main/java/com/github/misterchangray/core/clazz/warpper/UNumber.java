package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.util.ConverterUtil;

import java.math.BigInteger;
import java.util.Arrays;



public class UNumber  extends Number{
    private byte[] adata;

    public void set(byte[] abytes) {
        this.adata = abytes;
    }

    public static UNumber valueOf(long anumber) {
        return  valueOf(BigInteger.valueOf(anumber));
    }

    public static UNumber valueOf(BigInteger anumber) {
        UNumber uNumber = new UNumber();
        uNumber.setAdata(anumber);
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
    public BigInteger unsigned() {
        return ConverterUtil.byteToBigInteger(adata);
    }

    public UNumber signed(BigInteger signedNumber) {
        if(signedNumber.compareTo(BigInteger.ZERO) < 0) {
            byte[] adata = signedNumber.toByteArray();
            byte[] bytes = new byte[adata.length + 1];
            System.arraycopy(adata, 0, bytes, 1, adata.length);
            signedNumber = new BigInteger(bytes);
        }
        this.adata = ConverterUtil.bigIntegerToByte(signedNumber);

        return this;
    }


    public byte asSignedByte() {
        return adata[adata.length - 1] ;
    }

    public short asSignedShort() {
        long res = 0;

        for (int i = 0, start=Math.max(0, adata.length - 2);
             i < adata.length  ; i ++) {
            if(start > i ) {
                continue;
            }

            res <<= 8;
            res |= (0xff & adata[i]);
        }
        return (short) res;
    }

    public int asSignedInt() {
        long res = 0;
        for (int i = 0, start=Math.max(0, adata.length - 4);
             i < adata.length  ; i ++) {
            if(start > i ) {
                continue;
            }

            res <<= 8;
            res |= (0xff & adata[i]);
        }
        return (int) res;
    }

    public long asSignedLong() {
        long res = 0;
        for (int i = 0, start=Math.max(0, adata.length - 8);
             i < adata.length  ; i ++) {
            if(start > i ) {
                continue;
            }

            res <<= 8;
            res |= (0xff & adata[i]);
        }
        return  res;
    }



    /**
     * get of unsigned
     * @return
     */
    public byte[] raw() {
        return  adata;
    }


    public static UNumber build() {
        return new UNumber();
    }

    @Override
    public String toString() {
        return "UNumber{val=" + unsigned() + '}';
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

    public BigInteger getAdata() {
        return unsigned();
    }

    public void setAdata(BigInteger anumber) {
        if(anumber .compareTo(BigInteger.ZERO)  < 0) {
            throw  new MagicByteException("invalid unsigned number! should be greater than zero !");
        }

        this.adata = ConverterUtil.bigIntegerToByte(anumber);
    }

    @Override
    public int intValue() {
        return unsigned().intValue();
    }

    @Override
    public long longValue() {
        return unsigned().longValue();
    }

    @Override
    public float floatValue() {
        return unsigned().floatValue();
    }

    @Override
    public double doubleValue() {
        return unsigned().doubleValue();
    }
}
