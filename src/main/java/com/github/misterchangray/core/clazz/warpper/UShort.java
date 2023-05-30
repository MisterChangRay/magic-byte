package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.exception.MagicByteException;

import java.util.Objects;

public class UShort  extends Number{
    private int ashort;

    /**
     * get of unsigned
     * @return
     */
    public int unsigned() {
        return ashort;
    }

    public UShort signed(short ashort) {
        this.ashort =  ashort < 0 ? 0xFFFF  & ashort : ashort;;
        return this;
    }

    /**
     * get of signed
     * @return
     */
    public short signed() {
        return  (short) ashort;
    }

    public void set(int aUnsignedShort) {
       this.setAshort(aUnsignedShort);
    }

    public UShort(int aUnsignedShort) {
        this.set(aUnsignedShort);
    }

    public UShort() {
    }

    @Override
    public int intValue() {
        return ashort;
    }

    @Override
    public long longValue() {
        return ashort;
    }

    @Override
    public float floatValue() {
        return ashort;
    }

    @Override
    public double doubleValue() {
        return ashort;
    }

    public int getAshort() {
        return ashort;
    }

    public void setAshort(int aUnsignedShort) {
        if(aUnsignedShort < 0) {
            throw  new MagicByteException("invalid unsigned number! should be greater than zero !");
        }

        this.ashort = aUnsignedShort;
    }

    public static UShort build() {
        return new UShort();
    }

    public static UShort valueOf(int aUnsignedShort) {
        return new UShort(aUnsignedShort);
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
