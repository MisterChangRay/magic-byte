package com.github.misterchangray.core.dynamicsize.pojo.error;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.dynamicsize.pojo.nested.DynamicHead2;


@MagicClass
public class SimpleDuplicatedId {
    @MagicField(id = "len1", order = 1)
    private short len;
    @MagicField( order = 2)
    private int len2;
    @MagicField( id = "len1",order = 3)
    private UInt len3;

    public short getLen() {
        return len;
    }

    public void setLen(short len) {
        this.len = len;
    }

    public int getLen2() {
        return len2;
    }

    public void setLen2(int len2) {
        this.len2 = len2;
    }

    public UInt getLen3() {
        return len3;
    }

    public void setLen3(UInt len3) {
        this.len3 = len3;
    }
}
