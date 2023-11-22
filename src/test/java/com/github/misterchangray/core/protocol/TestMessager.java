package com.github.misterchangray.core.protocol;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.intf.MagicMessage;
import org.junit.Assert;
import org.junit.Test;

public class TestMessager {
    @Test
    public void testStudent() {
        // 注册消息命令,此消息类型写死到类中
        MagicByte.registerCMD(1, Student.class);
        Student student = new Student();
        student.setAge(13);
        student.setName("AA");

        // 头
        Head head = new Head();
        head.setLen(UInt.valueOf(17));

        // 组成完整协议对象
        Protocol protocol = new Protocol();
        protocol.setHead(head);
        protocol.setBody(student);

        // 序列化和反序列化
        byte[] bytes = MagicByte.unpackToByte(protocol);
        Protocol s1 = MagicByte.pack(bytes);

        // 可以通过 cmd() 函数获取当前消息类型, 前提是必须要实现接口方法
        int i = 1;

    }
}
