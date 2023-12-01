package com.github.misterchangray.core.dynamicsize.pojo.nested2;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class HeadBox1 {
    @MagicField( order = 1)
    private short len;


    public short getLen() {
        return len;
    }

    public void setLen(short len) {
        this.len = len;
    }


}
