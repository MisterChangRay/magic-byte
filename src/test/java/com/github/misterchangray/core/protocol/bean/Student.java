package com.github.misterchangray.core.protocol.bean;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.intf.MagicMessage;

import java.util.Objects;

@MagicClass
public class Student implements MagicMessage {

    @MagicField(order = 4)
    private int age;
    @MagicField(order = 5, size = 10)
    private String name;

    @Override
    public int cmd() {
        return 1;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
}
