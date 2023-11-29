package com.github.misterchangray.core.dynamicsize.pojo.nested;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class DynamicHead {
    @MagicField( order = 1)
    private short len;
    @MagicField( order = 2)
    private DynamicHead2 head2;

    public short getLen() {
        return len;
    }

    public void setLen(short len) {
        this.len = len;
    }

    public DynamicHead2 getHead2() {
        return head2;
    }

    public void setHead2(DynamicHead2 head2) {
        this.head2 = head2;
    }
}
