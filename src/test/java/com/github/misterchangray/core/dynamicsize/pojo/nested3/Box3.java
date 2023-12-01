package com.github.misterchangray.core.dynamicsize.pojo.nested3;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UByte;

public class Box3 {
    @MagicField( order = 1, calcLength = true)
    private UByte totalLen;
    @MagicField( order = 5)
    private UByte len;
    @MagicField(order = 9, dynamicSizeOf ="#box1.len")
    private String parentlen;

    public UByte getTotalLen() {
        return totalLen;
    }

    public void setTotalLen(UByte totalLen) {
        this.totalLen = totalLen;
    }

    public UByte getLen() {
        return len;
    }

    public void setLen(UByte len) {
        this.len = len;
    }

    public String getParentlen() {
        return parentlen;
    }

    public void setParentlen(String parentlen) {
        this.parentlen = parentlen;
    }
}
