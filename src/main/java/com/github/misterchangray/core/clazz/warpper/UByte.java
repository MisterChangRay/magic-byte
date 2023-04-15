package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.util.ConverterUtil;

import java.util.Objects;

/**
 * 对java中的byte进行包装
 *
 * 提供便捷的无符号数封装及访问
 *
 */
public class UByte {
    private byte abyte;

    /**
     * get of unsigned byte
     * @return
     */
    public short get() {
        return abyte < 0 ?  ConverterUtil.byteToUnsigned(abyte) : abyte;
    }

    /**
     * get of signed byte
     * @return
     */
    public byte raw() {
        return abyte;
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
