package com.github.misterchangray.core.dynamicsize.pojo.nested2;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UByte;

public class HeadBox2 {
    @MagicField( order = 1)
    private UByte len;
    @MagicField(order = 2, dynamicSizeOf ="#head1.len")
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
