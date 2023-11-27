package com.github.misterchangray.core.autochecker.pojo_error;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.autochecker.pojo.Staff;

import java.util.List;

@MagicClass
public class ErrorTypeWithCheckCode {
    @MagicField(order = 1)
    private int head;
    @MagicField(order = 3, calcLength = true)
    private int length;
    @MagicField(order = 5, size = 10)
    private String name;

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

    public List<Integer> getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(List<Integer> checkCode) {
        this.checkCode = checkCode;
    }

    @MagicField(order = 7, size = 10)
    private String addr;
    @MagicField(order = 9, size = 5, dynamicSize = true)
    private List<Staff> staffs;
    @MagicField(order = 13, calcCheckCode = true, size = 5)
    private List<Integer> checkCode;


}
