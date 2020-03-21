package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicField;

public class Teacher {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 3)
    private int age;
    @MagicField(order = 2)
    private Phone phone;
    @MagicField(order = 4)
    private Boolean sex;
    @MagicField(order = 5)
    private byte sexByte;
    @MagicField(order = 6)
    private char sexChar;

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

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public byte getSexByte() {
        return sexByte;
    }

    public void setSexByte(byte sexByte) {
        this.sexByte = sexByte;
    }

    public char getSexChar() {
        return sexChar;
    }

    public void setSexChar(char sexChar) {
        this.sexChar = sexChar;
    }
}
