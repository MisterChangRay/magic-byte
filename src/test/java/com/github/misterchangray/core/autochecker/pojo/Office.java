package com.github.misterchangray.core.autochecker.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

@MagicClass
public class Office {
    @MagicField(order = 1)
    private int head;
    @MagicField(order = 3, calcLength = true)
    private int length;
    @MagicField(order = 5, size = 10)
    private String name;
    @MagicField(order = 7, size = 10)
    private String addr;
    @MagicField(order = 9, size = 5, autoTrim = true)
    private List<Staff> staffs;
    @MagicField(order = 13, calcCheckCode = true)
    private byte checkCode;

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(byte checkCode) {
        this.checkCode = checkCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }
}
