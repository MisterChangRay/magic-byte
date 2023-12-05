package com.github.misterchangray.core.dynamicsize.pojo.nested4;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
@MagicClass
public class Box41 {
    @MagicField(order = 1)
    public int length;

    @MagicField(order = 2, dynamicSizeOf = "length")
    public byte[] data;
}
