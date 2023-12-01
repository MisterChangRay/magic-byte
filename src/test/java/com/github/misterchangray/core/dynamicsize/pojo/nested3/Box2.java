package com.github.misterchangray.core.dynamicsize.pojo.nested3;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UByte;

@MagicClass
public class Box2 {
    @MagicField( order = 1)
    private UByte len;
    @MagicField( order = 2)
    private Box3 box3;
    // 这里相对引用, 指向 box3.len
    @MagicField(order = 3, dynamicSizeOf ="box3.len")
    private String box3Str;
    // 这里是绝对引用，所以指向的是 BoxRoot.len
    @MagicField(order = 4, dynamicSizeOf ="len")
    private String parentlen;

    public UByte getLen() {
        return len;
    }

    public void setLen(UByte len) {
        this.len = len;
    }

    public Box3 getBox3() {
        return box3;
    }

    public void setBox3(Box3 box3) {
        this.box3 = box3;
    }

    public String getBox3Str() {
        return box3Str;
    }

    public void setBox3Str(String box3Str) {
        this.box3Str = box3Str;
    }

    public String getParentlen() {
        return parentlen;
    }

    public void setParentlen(String parentlen) {
        this.parentlen = parentlen;
    }
}
