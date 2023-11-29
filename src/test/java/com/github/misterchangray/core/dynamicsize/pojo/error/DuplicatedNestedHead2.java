package com.github.misterchangray.core.dynamicsize.pojo.error;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class DuplicatedNestedHead2 {
    @MagicField( order = 1)
    private short len;
    @MagicField( order = 2)
    private DuplicatedNestedHead head2;

    public short getLen() {
        return len;
    }

    public void setLen(short len) {
        this.len = len;
    }

    public DuplicatedNestedHead getHead2() {
        return head2;
    }

    public void setHead2(DuplicatedNestedHead head2) {
        this.head2 = head2;
    }
}
