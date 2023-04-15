package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.*;

@MagicClass()
public class AllDataTypes {
    @MagicField(order = 1)
    private byte b1;
    @MagicField(order = 2)
    private Byte b2;

    @MagicField(order = 3)
    private boolean bo1;
    @MagicField(order = 4)
    private Boolean bo2;

    @MagicField(order = 5)
    private char c1;
    @MagicField(order = 6)
    private Character c2;

    @MagicField(order = 7)
    private short s1;
    @MagicField(order = 8)
    private Short s2;

    @MagicField(order = 9)
    private int i1;
    @MagicField(order = 10)
    private Integer i2;

    @MagicField(order = 12)
    private long l1;
    @MagicField(order = 11)
    private Long l2;

    @MagicField(order = 14)
    private float f1;
    @MagicField(order = 13)
    private Float f2;


    @MagicField(order = 15)
    private double d1;
    @MagicField(order = 16)
    private Double d2;

    @MagicField(order = 17, size = 10)
    private String st2;

    @MagicField(order = 18)
    private UByte uByte;
    @MagicField(order = 19)
    private UShort uShort;
    @MagicField(order = 21)
    private UInt uInt;
    @MagicField(order = 23)
    private ULong uLong;
    @MagicField(order = 25, size = 10)
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

    public byte getB1() {
        return b1;
    }

    public void setB1(byte b1) {
        this.b1 = b1;
    }

    public Byte getB2() {
        return b2;
    }

    public void setB2(Byte b2) {
        this.b2 = b2;
    }

    public boolean isBo1() {
        return bo1;
    }

    public void setBo1(boolean bo1) {
        this.bo1 = bo1;
    }

    public Boolean getBo2() {
        return bo2;
    }

    public void setBo2(Boolean bo2) {
        this.bo2 = bo2;
    }

    public char getC1() {
        return c1;
    }

    public void setC1(char c1) {
        this.c1 = c1;
    }

    public Character getC2() {
        return c2;
    }

    public void setC2(Character c2) {
        this.c2 = c2;
    }

    public short getS1() {
        return s1;
    }

    public void setS1(short s1) {
        this.s1 = s1;
    }

    public Short getS2() {
        return s2;
    }

    public void setS2(Short s2) {
        this.s2 = s2;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public Integer getI2() {
        return i2;
    }

    public void setI2(Integer i2) {
        this.i2 = i2;
    }

    public long getL1() {
        return l1;
    }

    public void setL1(long l1) {
        this.l1 = l1;
    }

    public Long getL2() {
        return l2;
    }

    public void setL2(Long l2) {
        this.l2 = l2;
    }

    public float getF1() {
        return f1;
    }

    public void setF1(float f1) {
        this.f1 = f1;
    }

    public Float getF2() {
        return f2;
    }

    public void setF2(Float f2) {
        this.f2 = f2;
    }

    public double getD1() {
        return d1;
    }

    public void setD1(double d1) {
        this.d1 = d1;
    }

    public Double getD2() {
        return d2;
    }

    public void setD2(Double d2) {
        this.d2 = d2;
    }

    public String getSt2() {
        return st2;
    }

    public void setSt2(String st2) {
        this.st2 = st2;
    }
}
