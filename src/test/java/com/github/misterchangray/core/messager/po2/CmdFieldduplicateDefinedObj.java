package com.github.misterchangray.core.messager.po2;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.intf.MagicMessage;

@MagicClass
public class CmdFieldduplicateDefinedObj implements MagicMessage {
    @MagicField(order = 1)
    private byte head;
    @MagicField(order = 2, calcLength = true)
    private UInt len;
    @MagicField(order = 3, cmdField = true)
    private byte cmd = 33;
    @MagicField(order = 4, cmdField = true)
    private int age;
    @MagicField(order = 5, size = 10)
    private String name;

    public byte getHead() {
        return head;
    }

    public void setHead(byte head) {
        this.head = head;
    }

    public UInt getLen() {
        return len;
    }

    public void setLen(UInt len) {
        this.len = len;
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
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
}
