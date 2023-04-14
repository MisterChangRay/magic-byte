package com.github.misterchangray.core.clazz.warpper;

import java.util.Objects;

public class UShort {
    private short ashort;

    public int get() {
        return ashort;
    }

    public void set(short i) {
        this.ashort = i;
    }

    public UShort(short ashort) {
        this.ashort = ashort;
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
