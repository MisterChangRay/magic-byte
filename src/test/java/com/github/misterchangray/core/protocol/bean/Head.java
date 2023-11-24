package com.github.misterchangray.core.protocol.bean;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UInt;

import java.util.Objects;

@MagicClass
public class Head {
    @MagicField(order = 1, defaultVal = 0xA8)
    private byte head;
    @MagicField(order = 2)
    private UInt len;
    @MagicField(order = 3)
    private byte cmd;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Head head1 = (Head) o;
        return head == head1.head && cmd == head1.cmd && Objects.equals(len, head1.len);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, len, cmd);
    }
}
