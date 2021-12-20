package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 16:33
 **/
public class Student {
    @MagicField(order = 1, size = 10, autoTrim = true)
    private String name;
    @MagicField(order = 2)
    private long phone;
    @MagicField(order = 3, size = 3)
    private int[] bookids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int[] getBookids() {
        return bookids;
    }

    public void setBookids(int[] bookids) {
        this.bookids = bookids;
    }
}
