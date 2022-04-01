package com.github.misterchangray.core.autotrim.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.common.simple.ByteObj;

import java.util.List;

@MagicClass
public class AutoTrimObj {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 2, dynamicSize = true, size = 5)
    private List<ByteObj> boodsId;
    @MagicField(order = 3)
    private int age;



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

    public List<ByteObj> getBoodsId() {
        return boodsId;
    }

    public void setBoodsId(List<ByteObj> boodsId) {
        this.boodsId = boodsId;
    }
}
