package com.github.misterchangray.core.dynamicsize.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class DynamicSizeNesting {
    @MagicField(order = 1, size = 10)
    private String name;

    @MagicField(order = 3)
    private DynamicSizeArray dynamicSizeArray;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DynamicSizeArray getDynamicSizeArray() {
        return dynamicSizeArray;
    }

    public void setDynamicSizeArray(DynamicSizeArray dynamicSizeArray) {
        this.dynamicSizeArray = dynamicSizeArray;
    }
}
