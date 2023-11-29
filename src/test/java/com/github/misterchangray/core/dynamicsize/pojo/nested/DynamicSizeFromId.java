package com.github.misterchangray.core.dynamicsize.pojo.nested;

import com.github.misterchangray.core.annotation.MagicField;

public class DynamicSizeFromId {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 2)
    private DynamicHead head;

    @MagicField(order = 3, dynamicSizeOf ="head.len")
    private int[] boodsId;
    @MagicField(order = 5)
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DynamicHead getHead() {
        return head;
    }

    public void setHead(DynamicHead head) {
        this.head = head;
    }

    public int[] getBoodsId() {
        return boodsId;
    }

    public void setBoodsId(int[] boodsId) {
        this.boodsId = boodsId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
