package com.github.misterchangray.core.example;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.MResult;
import org.junit.Assert;
import org.junit.Test;

public class TestExample {
    /**
     * 一个简单的使用例子
     *
     * 介绍框架的基本使用方式： 定义一个head, 和body, 解析时先解析head, 然后根据head中的类容进行业务处理
     *
     * v2.4.0 后新增消息注册功能，即可直接解析到目标消息
     */
    @Test
    public void testExample() {
        byte[] tmp = buildDeviceMessage();

        MResult<Head> headMResult = MagicByte.<Head>packExt(tmp, Head.class);
        Assert.assertEquals(Integer.valueOf(3) ,headMResult.getBytes());
        Head head = headMResult.getData();
        Assert.assertEquals(head.getCmd(), (byte)0x01);
        Assert.assertEquals(head.getHeader(), (byte)0xA1);
        Assert.assertEquals(head.getLength(), 12);
        if(head.getCmd() == 0x01) {
            // heatbeat message
            HeartbeatCmd heartbeatCmd = MagicByte.pack(tmp, HeartbeatCmd.class);
            Assert.assertEquals(heartbeatCmd.getCheckCode(), 0x12);
            Assert.assertEquals(heartbeatCmd.getTimestamp(), 1682043007448L);

        } else {
            throw  new RuntimeException("invalid command!");
        }



    }

    private byte[] buildDeviceMessage() {
        Head head = new Head();
        head.setHeader((byte) 0XA1);
        head.setCmd((byte) 0x01);
        HeartbeatCmd heartbeatCmd = new HeartbeatCmd();
        heartbeatCmd.setHead(head);
        heartbeatCmd.setTimestamp(1682043007448L);
        heartbeatCmd.setCheckCode((byte) 0x12);

        return MagicByte.unpackToByte(heartbeatCmd);
    }
}
