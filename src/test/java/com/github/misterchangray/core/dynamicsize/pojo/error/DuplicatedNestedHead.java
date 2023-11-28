package com.github.misterchangray.core.dynamicsize.pojo.error;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UByte;

public class DuplicatedNestedHead {
    @MagicField(id = "len2", order = 1)
    private UByte len;
    @MagicField(id = "len1", order = 2, size = 10)
    private String fu;

    public UByte getLen() {
        return len;
    }

    public void setLen(UByte len) {
        this.len = len;
    }

    public String getFu() {
        return fu;
    }

    public void setFu(String fu) {
        this.fu = fu;
    }
}
