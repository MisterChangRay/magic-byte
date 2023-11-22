package com.github.misterchangray.core.protocol;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UInt;

@MagicClass
public class Head {
    @MagicField(order = 1, defaultVal = 0xA8)
    private byte head;
    @MagicField(order = 2)
    private UInt len;
    @MagicField(order = 3, cmdField = true)
    private byte cmd = 2;

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
}
