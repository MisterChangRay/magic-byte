package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 16:32
 **/
public class Teacher {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2, size = 10, autoTrim = true)
    private String name;
    @MagicField(order = 3)
    private int phoneSize;
    @MagicField(order = 4, dynamicSizeOf = 3)
    private long[] phones;



    public static List<Teacher> build(int count) {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i <count; i++) {

            Teacher teacher = new Teacher();
            teacher.setName("teacher1");
            teacher.setId(10 + i);
            teacher.setPhoneSize(3);
            teacher.setPhones(new long[teacher.getPhoneSize()]);
            for (int j = 0; j < teacher.getPhoneSize(); j++) {
                teacher.getPhones()[j] = 18300000 + j;
            }
            teachers.add(teacher);
        }
        return teachers;
    }


    public int getPhoneSize() {
        return phoneSize;
    }

    public void setPhoneSize(int phoneSize) {
        this.phoneSize = phoneSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long[] getPhones() {
        return phones;
    }

    public void setPhones(long[] phones) {
        this.phones = phones;
    }
}
