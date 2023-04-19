package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.exception.MagicByteException;

import java.math.BigInteger;
import java.util.Objects;

public class ULong {
    private BigInteger along;
    private BigInteger aor =  new BigInteger("FFFFFFFFFFFFFFFF", 16);

    /**
     *  get of unsigned
     * @return
     */
    public BigInteger get() {
        return  along;
    }

    /**
     * get of signed
     * @return
     */
    public long raw() {
        return along.longValue();
    }

    public ULong raw(long along) {
        BigInteger l = BigInteger.valueOf(along);
        this.along = along < 0 ? l.and(aor) : l;
        return this;
    }

    public BigInteger getAlong() {
        return along;
    }


    public ULong(BigInteger along) {
        if(along.compareTo(BigInteger.ZERO) < 0) {
            throw  new MagicByteException("invalid unsigned number! should be greater than zero !");
        }

        this.along = along;
    }

    public ULong() {

    }
    public static ULong build() {
        return new ULong();
    }


    public static ULong valueOf(long along) {
        return valueOf(BigInteger.valueOf(along));
    }


    public static ULong valueOf(BigInteger along) {
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
        return along.equals( uLong.along);
    }

    @Override
    public int hashCode() {
        return Objects.hash(along);
    }
}
