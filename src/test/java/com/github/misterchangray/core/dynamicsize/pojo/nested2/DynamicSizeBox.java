package com.github.misterchangray.core.dynamicsize.pojo.nested2;

import com.github.misterchangray.core.annotation.MagicField;

public class DynamicSizeBox {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 2)
    private HeadBox1 head1;

    @MagicField(order = 3)
    private HeadBox2 head2;


    @MagicField(order = 4, dynamicSizeOf = "head1.len")
    private byte[] data;

    @MagicField(order = 5, dynamicSizeOf = "#head2.len")
    private byte[] data2;

    @MagicField(order = 6)
    private int age;


    public byte[] getData2() {
        return data2;
    }

    public void setData2(byte[] data2) {
        this.data2 = data2;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeadBox1 getHead1() {
        return head1;
    }

    public void setHead1(HeadBox1 head1) {
        this.head1 = head1;
    }

    public HeadBox2 getHead2() {
        return head2;
    }

    public void setHead2(HeadBox2 head2) {
        this.head2 = head2;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
