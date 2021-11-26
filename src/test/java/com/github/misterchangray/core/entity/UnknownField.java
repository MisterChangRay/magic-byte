package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.*;

@MagicClass(autoTrim = false)
public class UnknownField {
    private Date time;
    private StringBuffer stringBuffer;
    private StringBuilder stringBuilder;
    @MagicField(order = 4, size = 10, autoTrim = true)
    private String name;
    @MagicField(order = 5)
    private float aFloat;
    @MagicField(order = 6)
    private double aDouble;
    private Object tmo;
    private List<String> a;
    @MagicField(order = 7)
    private TreeMap<String, String > b;
    @MagicField(order = 8)
    private long createTime;


    public List<String> getA() {
        return a;
    }

    public void setA(List<String> a) {
        this.a = a;
    }

    public TreeMap<String, String> getB() {
        return b;
    }

    public void setB(TreeMap<String, String> b) {
        this.b = b;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public static void main(String[] args) {
        System.out.println(Map.class.equals(HashMap.class));
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public StringBuffer getStringBuffer() {
        return stringBuffer;
    }

    public void setStringBuffer(StringBuffer stringBuffer) {
        this.stringBuffer = stringBuffer;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getaFloat() {
        return aFloat;
    }

    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public double getaDouble() {
        return aDouble;
    }

    public void setaDouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public Object getTmo() {
        return tmo;
    }

    public void setTmo(Object tmo) {
        this.tmo = tmo;
    }
}
