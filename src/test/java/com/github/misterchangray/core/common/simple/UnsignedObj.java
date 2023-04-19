package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.*;

@MagicClass
public class UnsignedObj {
    @MagicField(order = 1)
    private UByte uByte;
    @MagicField(order = 2)
    private UShort uShort;
    @MagicField(order = 3)
    private UInt uInt;
    @MagicField(order = 4)
    private ULong uLong;
    @MagicField(order = 5, size = 4)
    private UNumber uNumber;


    public UByte getuByte() {
        return uByte;
    }

    public void setuByte(UByte uByte) {
        this.uByte = uByte;
    }

    public UShort getuShort() {
        return uShort;
    }

    public void setuShort(UShort uShort) {
        this.uShort = uShort;
    }

    public UInt getuInt() {
        return uInt;
    }

    public void setuInt(UInt uInt) {
        this.uInt = uInt;
    }

    public ULong getuLong() {
        return uLong;
    }

    public void setuLong(ULong uLong) {
        this.uLong = uLong;
    }

    public UNumber getuNumber() {
        return uNumber;
    }

    public void setuNumber(UNumber uNumber) {
        this.uNumber = uNumber;
    }
}
