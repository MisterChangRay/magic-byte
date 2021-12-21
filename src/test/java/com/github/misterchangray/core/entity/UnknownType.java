package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 16:32
 **/
public class UnknownType {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2, size = 10, autoTrim = true)
    private String name;
    @MagicField(order = 3)
    private int phoneSize;
    @MagicField(order = 4, dynamicSizeOf = 3)
    private long[] phones;
    @MagicField(order = 5)
    private Date brithday;
    @MagicField(order = 6)
    private Object attr;



    public static List<UnknownType> build(int count) {
        List<UnknownType> teachers = new ArrayList<>();
        for (int i = 0; i <count; i++) {

            UnknownType teacher = new UnknownType();
            teacher.setName("teacher1");
            teacher.setId(10 + i);
            teacher.setPhoneSize(3);
            teacher.setPhones(new long[teacher.getPhoneSize()]);
            teacher.setAttr("Asdfasdf");
            teacher.setBrithday(new Date());
            for (int j = 0; j < teacher.getPhoneSize(); j++) {
                teacher.getPhones()[j] = 12100000 + j;
            }
            teachers.add(teacher);
        }
        return teachers;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public Object getAttr() {
        return attr;
    }

    public void setAttr(Object attr) {
        this.attr = attr;
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
