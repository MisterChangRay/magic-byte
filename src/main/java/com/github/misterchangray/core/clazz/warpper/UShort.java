package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.exception.MagicByteException;

import java.util.Objects;

public class UShort {
    private int ashort;

    /**
     * get of unsigned
     * @return
     */
    public int get() {
        return ashort;
    }

    public UShort raw(short ashort) {
        this.ashort =  ashort < 0 ? 0xFFFF  & ashort : ashort;;
        return this;
    }

    /**
     * get of signed
     * @return
     */
    public short raw() {
        return  (short) ashort;
    }

    public void set(int i) {
        this.ashort = i;
    }

    public UShort(int ashort) {
        if(ashort < 0) {
            throw  new MagicByteException("invalid unsigned number! should be greater than zero !");
        }
        this.ashort = ashort;
    }

    public UShort() {
    }

    public int getAshort() {
        return ashort;
    }

    public void setAshort(int ashort) {
        this.ashort = ashort;
    }

    public static UShort build() {
        return new UShort();
    }

    public static UShort valueOf(int ashort) {
        return new UShort(ashort);
    }

    @Override
    public String toString() {
        return "UShort{val=" + ashort + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UShort uShort = (UShort) o;
        return ashort == uShort.ashort;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ashort);
    }
}
