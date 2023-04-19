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
public class UByte {
    private short abyte;

    /**
     * get of unsigned byte
     * @return
     */
    public short get() {
        return abyte;
    }

    /**
     * get of signed byte
     * @return
     */
    public byte raw() {
        return (byte)abyte;
    }

    public UByte raw(byte abyte) {
        this.abyte = abyte < 0 ?  ConverterUtil.byteToUnsigned(abyte) : abyte;
        return this;
    }

    public void set(short abyte) {
        this.abyte = abyte;
    }

    public static UByte build() {
        return new UByte();
    }

    public UByte () {

    }


    public UByte(short abyte) {
        if(abyte < 0) {
            throw  new MagicByteException("invalid unsigned number! should be greater than zero !");
        }

        this.abyte = abyte;
    }


    public static UByte valueOf(short abyte) {
        return new UByte(abyte);
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
}
