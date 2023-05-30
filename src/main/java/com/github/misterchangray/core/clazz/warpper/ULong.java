package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.exception.MagicByteException;

import java.math.BigInteger;
import java.util.Objects;

public class ULong  extends Number{
    private BigInteger along;
    private BigInteger aor =  new BigInteger("FFFFFFFFFFFFFFFF", 16);

    /**
     *  get of unsigned
     * @return
     */
    public BigInteger unsigned() {
        return  along;
    }

    /**
     * get of signed
     * @return
     */
    public long signed() {
        return along.longValue();
    }

    public ULong signed(long along) {
        BigInteger l = BigInteger.valueOf(along);
        this.along = along < 0 ? l.and(aor) : l;
        return this;
    }

    public BigInteger getAlong() {
        return along;
    }

    public void set(long aUnsignedLong) {
        setAlong(BigInteger.valueOf(aUnsignedLong));
    }

    public void setAlong(BigInteger aUnsignedLong) {
        if(aUnsignedLong .compareTo(BigInteger.ZERO)  < 0) {
            throw  new MagicByteException("invalid unsigned number! should be greater than zero !");
        }

        this.along = aUnsignedLong;
    }

    public ULong(BigInteger aUnsignedLong) {
        this.setAlong(aUnsignedLong);
    }

    public ULong() {

    }

    @Override
    public int intValue() {
        return along.intValue();
    }

    @Override
    public long longValue() {
        return along.longValue();
    }

    @Override
    public float floatValue() {
        return along.floatValue();
    }

    @Override
    public double doubleValue() {
        return along.doubleValue();
    }

    public static ULong build() {
        return new ULong();
    }


    public static ULong valueOf(long along) {
        return valueOf(BigInteger.valueOf(along));
    }


    public static ULong valueOf(BigInteger aUnsignedLong) {
        return new ULong(aUnsignedLong);
    }


    @Override
    public String toString() {
        return "ULong{val=" + unsigned() + '}';
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
