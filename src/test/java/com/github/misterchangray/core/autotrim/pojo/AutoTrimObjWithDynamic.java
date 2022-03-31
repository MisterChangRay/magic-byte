package com.github.misterchangray.core.autotrim.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.common.dynamic.DynamicStudent;
import com.github.misterchangray.core.common.simple.ByteObj;

import java.util.List;

@MagicClass
public class AutoTrimObjWithDynamic {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 5, dynamicSize = true, size = 5)
    private List<ByteObj> boodsId;
    @MagicField(order = 8)
    private DynamicStudent student;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ByteObj> getBoodsId() {
        return boodsId;
    }

    public void setBoodsId(List<ByteObj> boodsId) {
        this.boodsId = boodsId;
    }

    public DynamicStudent getStudent() {
        return student;
    }

    public void setStudent(DynamicStudent student) {
        this.student = student;
    }
}
