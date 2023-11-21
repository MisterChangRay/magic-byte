package com.github.misterchangray.core.messager.po;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.intf.MagicMessage;

@MagicClass
public class Teacher implements MagicMessage {
    @MagicField(order = 1)
    private Head head;
    @MagicField(order = 4)
    private int age;
    @MagicField(order = 5, size = 10)
    private String name;
    @Override
    public int cmd() {
        return this.head.getCmd();
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
