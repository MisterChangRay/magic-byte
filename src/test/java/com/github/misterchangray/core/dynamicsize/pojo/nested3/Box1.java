package com.github.misterchangray.core.dynamicsize.pojo.nested3;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UByte;

@MagicClass
public class Box1 {
    @MagicField( order = 1)
    private UByte len;
    @MagicField(order = 2, dynamicSizeOf ="#len")
    private String lenStr;
    @MagicField(order = 3, dynamicSizeOf ="len")
    private String len2Str;

    public UByte getLen() {
        return len;
    }

    public void setLen(UByte len) {
        this.len = len;
    }

    public String getLenStr() {
        return lenStr;
    }

    public void setLenStr(String lenStr) {
        this.lenStr = lenStr;
    }

    public String getLen2Str() {
        return len2Str;
    }

    public void setLen2Str(String len2Str) {
        this.len2Str = len2Str;
    }
}
