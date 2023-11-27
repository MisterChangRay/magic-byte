package com.github.misterchangray.core.autochecker.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;


@MagicClass(strict = true)
public class CheckCode4BytesWithListByte {
    @MagicField(order = 1)
    private int head;
    @MagicField(order = 3, calcLength = true)
    private int length;
    @MagicField(order = 5, size = 10)
    private String name;
    @MagicField(order = 7, size = 10)
    private String addr;
    @MagicField(order = 8, size = 4, calcCheckCode = true)
    private List<Byte> checkCodes;

    public List<Byte> getCheckCodes() {
        return checkCodes;
    }

    public void setCheckCodes(List<Byte> checkCodes) {
        this.checkCodes = checkCodes;
    }

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
}