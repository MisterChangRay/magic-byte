package com.github.misterchangray.core.example;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

/**
 * 报文头
 * 一般来说为公共结构, 即每个数据都应该有此字段
 */
@MagicClass
public class Head {
    @MagicField(order = 1)
    private byte header;
    // 此处将会在序列化时自动填充长度至此字段
    @MagicField(order = 3, calcLength = true)
    private byte length;
    @MagicField(order = 5)
    private byte cmd;

    public byte getHeader() {
        return header;
    }

    public void setHeader(byte header) {
        this.header = header;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }
}
