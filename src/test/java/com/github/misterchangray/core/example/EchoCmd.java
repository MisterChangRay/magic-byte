package com.github.misterchangray.core.example;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

/**
 * 指定的业务报文
 *
 * 一般来说，这里应该是业务报文，
 * 这里假设设备端存在一个回响报文, 设备端原样返回云端下发数据
 */
@MagicClass()
public class EchoCmd {
    // 这里组合使用 Head 结构
    @MagicField(order = 1)
    private Head head;
    @MagicField(order = 3, size = 10)
    private String body;
    @MagicField(order = 5)
    private byte checkCode;


    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(byte checkCode) {
        this.checkCode = checkCode;
    }
}
