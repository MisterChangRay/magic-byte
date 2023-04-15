package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.util.ConverterUtil;

import java.math.BigInteger;
import java.util.Objects;

public class ULong {
    private long along;
    private BigInteger aor =  new BigInteger("FFFFFFFFFFFFFFFF", 16);

    /**
     *  get of unsigned
     * @return
     */
    public BigInteger get() {
        BigInteger  l = BigInteger.valueOf(along);
        return along < 0 ? l.and(aor) : l;
    }


    /**
     * get of signed
     * @return
     */
    public long raw() {
        return along;
    }

    public void set(long i) {
        this.along = i;
    }

    public ULong(long along) {
        this.along = along;
    }

    public ULong() {

    }

    public static ULong valueOf(long along) {
        return new ULong(along);
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
