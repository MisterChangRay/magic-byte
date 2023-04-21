package com.github.misterchangray.core.example;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

/**
 * 心跳报文
 *
 * 一般来说会周期性上报
 */
@MagicClass()
public class HeartbeatCmd {
    // 这里组合使用 Head 结构
    @MagicField(order = 1)
    private Head head;
    @MagicField(order = 3)
    private long timestamp;
    @MagicField(order = 5)
    private byte checkCode;


    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(byte checkCode) {
        this.checkCode = checkCode;
    }
}
