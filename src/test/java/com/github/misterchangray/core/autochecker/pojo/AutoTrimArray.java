package com.github.misterchangray.core.autochecker.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class AutoTrimArray {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 3)
    private int age;
    @MagicField(order = 5, autoTrim = true, size = 5)
    private int[] boodsId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int[] getBoodsId() {
        return boodsId;
    }

    public void setBoodsId(int[] boodsId) {
        this.boodsId = boodsId;
    }
}
