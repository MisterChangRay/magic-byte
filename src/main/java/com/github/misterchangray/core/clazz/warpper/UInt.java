package com.github.misterchangray.core.clazz.warpper;

import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.util.ConverterUtil;

import java.util.Objects;

public class UInt {
    private long aint;

    /**
     *  get of unsigned
     * @return
     */
    public long get() {
        return aint;
    }

    /**
     * get of signed
     * @return
     */
    public int raw() {
        return  (int)aint;
    }

    public UInt raw(int aint) {
        this.aint = aint < 0 ? aint  & 0xFFFFFFFFL : aint;
        return  this;
    }

    public void set(long aint) {
        this.aint = aint;
    }

    public UInt(long aint) {
        if(aint < 0) {
            throw  new MagicByteException("invalid unsigned number! should be greater than zero !");
        }

        this.aint = aint;
    }

    public static UInt build() {
        return new UInt();
    }

    public UInt() {

    }

    public static UInt valueOf(long aint) {
        return new UInt(aint);
    }

    @Override
    public String toString() {
        return "UInt{val=" + aint + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UInt uInt = (UInt) o;
        return aint == uInt.aint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aint);
    }
}
