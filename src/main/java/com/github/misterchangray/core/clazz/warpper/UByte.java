package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.util.ConverterUtil;

import java.util.Objects;

/**
 * 对java中的byte进行包装
 *
 * 提供便捷的无符号数封装及访问
 *
 */
public class UByte extends Number{
    private short abyte;



    /**
     * get of unsigned byte
     * @return
     */
    public short unsigned() {
        return abyte;
    }

    /**
     * get of signed byte
     * @return
     */
    public byte signed() {
        return (byte)abyte;
    }

    public UByte signed(byte abyte) {
        this.abyte = abyte < 0 ?  ConverterUtil.byteToUnsigned(abyte) : abyte;
        return this;
    }

    public void set(short aUnsignedByte) {
        this.setAbyte(aUnsignedByte);
    }

    public static UByte build() {
        return new UByte();
    }

    public UByte () {

    }

    @Override
    public int intValue() {
        return abyte;
    }

    @Override
    public long longValue() {
        return abyte;
    }

    @Override
    public float floatValue() {
        return abyte;
    }

    @Override
    public double doubleValue() {
        return abyte;
    }


    public UByte(short aUnsignedByte) {
        this.set(aUnsignedByte);
    }


    public static UByte valueOf(short aUnsignedByte) {
        return new UByte(aUnsignedByte);
    }


    @Override
    public String toString() {
        return "UByte{val=" + abyte + '}';
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

    public short getAbyte() {
        return abyte;
    }

    public void setAbyte(short aUnsignedByte) {
        if(aUnsignedByte < 0) {
            throw  new MagicByteException("invalid unsigned number! should be greater than zero !");
        }

        this.abyte = aUnsignedByte;
    }
}
