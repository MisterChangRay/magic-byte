package com.github.misterchangray.core.autochecker.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UByte;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.clazz.warpper.UShort;

import java.util.List;

// OfficeWithUnsigend(34) bytes + staff(22byte)
@MagicClass
public class OfficeWithUnsigend {
    @MagicField(order = 1)
    private int head;
    @MagicField(order = 3, calcLength = true)
    private UInt length;
    @MagicField(id="4", order = 4)
    private UByte nameLen;
    @MagicField(order = 5, dynamicSizeOfId = "4")
    private String name;
    @MagicField(order = 7, size = 10)
    private String addr;
    @MagicField(id="8", order = 8)
    private UInt stafLen;
    @MagicField(order = 9, dynamicSizeOfId = "8")
    private List<Staff> staffs;
    @MagicField(order = 13, calcCheckCode = true)
    private UShort checkCode;

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public UByte getNameLen() {
        return nameLen;
    }

    public void setNameLen(UByte nameLen) {
        this.nameLen = nameLen;
    }

    public UInt getLength() {
        return length;
    }

    public void setLength(UInt length) {
        this.length = length;
    }

    public UInt getStafLen() {
        return stafLen;
    }

    public void setStafLen(UInt stafLen) {
        this.stafLen = stafLen;
    }

    public UShort getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(UShort checkCode) {
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
