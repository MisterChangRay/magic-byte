package com.github.misterchangray.core.dynamicsize.pojo.nested4;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;
@MagicClass
public class BoxRoot4 {
    @MagicField(order = 1)
    public int length;

    @MagicField(order = 2, dynamicSizeOf = "length")
    public String value;

    @MagicField(order = 3)
    public int dataLength;

    @MagicField(order = 4, dynamicSizeOf = "dataLength")
    public List<Box41> data;


}
