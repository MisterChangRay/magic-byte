package com.github.misterchangray.core.clazz.warpper;

import java.math.BigInteger;
import java.util.Objects;

public class ULong {
    private long along;

    public BigInteger get() {
        return BigInteger.valueOf(along);
    }

    public void set(long i) {
        this.along = i;
    }

    public ULong(long along) {
        this.along = along;
    }

    @Override
    public String toString() {
        return "ULong{val=" + get() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ULong uLong = (ULong) o;
        return along == uLong.along;
    }

    @Override
    public int hashCode() {
        return Objects.hash(along);
    }
}
