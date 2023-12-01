package com.github.misterchangray.core.dynamicsize.pojo.nested3;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class BoxRoot {
    @MagicField(order = 1)
    private byte len;
    @MagicField(order = 2)
    private Box1 box1;
    @MagicField(order = 3)
    private Box2 box2;
    @MagicField(order = 4, dynamicSizeOf ="box2.box3.len")
    private String box2_box3lenStr;

    @MagicField(order = 5, dynamicSizeOf ="len")
    private String rootLenStr;


    public String getRootLenStr() {
        return rootLenStr;
    }

    public void setRootLenStr(String rootLenStr) {
        this.rootLenStr = rootLenStr;
    }

    public String getBox2_box3lenStr() {
        return box2_box3lenStr;
    }

    public void setBox2_box3lenStr(String box2_box3lenStr) {
        this.box2_box3lenStr = box2_box3lenStr;
    }

    public byte getLen() {
        return len;
    }

    public void setLen(byte len) {
        this.len = len;
    }

    public Box1 getBox1() {
        return box1;
    }

    public void setBox1(Box1 box1) {
        this.box1 = box1;
    }

    public Box2 getBox2() {
        return box2;
    }

    public void setBox2(Box2 box2) {
        this.box2 = box2;
    }
}
