package com.github.misterchangray.core.autochecker.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class AutoTrimNesting {
    @MagicField(order = 1, size = 10)
    private String name;

    @MagicField(order = 3)
    private AutoTrimArray autoTrimArray;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AutoTrimArray getAutoTrimArray() {
        return autoTrimArray;
    }

    public void setAutoTrimArray(AutoTrimArray autoTrimArray) {
        this.autoTrimArray = autoTrimArray;
    }
}
