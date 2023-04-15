package com.github.misterchangray.core.clazz.warpper;

import java.util.Objects;

public class UShort {
    private short ashort;

    /**
     * get of unsigned
     * @return
     */
    public int get() {
        return ashort < 0 ? 0xFFFF  & ashort : ashort;
    }


    /**
     * get of signed
     * @return
     */
    public short raw() {
        return  ashort;
    }

    public void set(short i) {
        this.ashort = i;
    }

    public UShort(short ashort) {
        this.ashort = ashort;
    }

    public UShort() {

    }

    public static UShort valueOf(short ashort) {
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
