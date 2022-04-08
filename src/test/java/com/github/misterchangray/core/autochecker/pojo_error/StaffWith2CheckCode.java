package com.github.misterchangray.core.autochecker.pojo_error;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class StaffWith2CheckCode {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 3, calcCheckCode = true)
    private int age;
    @MagicField(order = 5)
    private long phone;

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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
