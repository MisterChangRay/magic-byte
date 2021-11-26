package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;


@MagicClass(autoTrim = true, strict = true)
public class Student {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 2, size = 5)
    private byte[] bookIds;
    @MagicField(order = 3)
    private Integer age;
    @MagicField(order = 4, size = 3)
    private long[] phones;

    public void setAge(Integer age) {
        this.age = age;
    }


    public long[] getPhones() {
        return phones;
    }

    public void setPhones(long[] phones) {
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBookIds() {
        return bookIds;
    }

    public void setBookIds(byte[] bookIds) {
        this.bookIds = bookIds;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


