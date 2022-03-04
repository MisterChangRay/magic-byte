package com.github.misterchangray.core.common.entity.custom;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass()
public class CustomDefaultValue {
    @MagicField(order = 2, defaultVal = 3)
    private Byte b2;

    @MagicField(order = 4, defaultVal = 4)
    private Boolean bo2;

    @MagicField(order = 6, defaultVal = 6)
    private Character c2;

    @MagicField(order = 8, defaultVal = 8)
    private Short s2;

    @MagicField(order = 10, defaultVal = 10)
    private Integer i2;

    @MagicField(order = 11, defaultVal = 11)
    private Long l2;

    @MagicField(order = 13, defaultVal = 13)
    private Float f2;

    @MagicField(order = 16, defaultVal = 16)
    private Double d2;

    @MagicField(order = 17, size = 10)
    private String st2;


    public Byte getB2() {
        return b2;
    }

    public void setB2(Byte b2) {
        this.b2 = b2;
    }

    public Boolean getBo2() {
        return bo2;
    }

    public void setBo2(Boolean bo2) {
        this.bo2 = bo2;
    }

    public Character getC2() {
        return c2;
    }

    public void setC2(Character c2) {
        this.c2 = c2;
    }

    public Short getS2() {
        return s2;
    }

    public void setS2(Short s2) {
        this.s2 = s2;
    }

    public Integer getI2() {
        return i2;
    }

    public void setI2(Integer i2) {
        this.i2 = i2;
    }

    public Long getL2() {
        return l2;
    }

    public void setL2(Long l2) {
        this.l2 = l2;
    }

    public Float getF2() {
        return f2;
    }

    public void setF2(Float f2) {
        this.f2 = f2;
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
