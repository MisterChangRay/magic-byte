package com.github.misterchangray.core.protocol;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.protocol.bean.Head;
import com.github.misterchangray.core.protocol.bean.Protocol;
import com.github.misterchangray.core.protocol.bean.Student;
import com.github.misterchangray.core.protocol.bean.Teacher;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class TestMessager {
    @Test
    public void testStudent() {

        MagicByte.configDefaultCharset(StandardCharsets.UTF_8);

        // 数据体
        Student student = new Student();
        student.setAge(13);
        student.setName("AA");

        Teacher teacher = new Teacher();
        teacher.setAge(44);
        teacher.setName("李老师");

        // 头
        Head head = new Head();
        head.setLen(UInt.valueOf(17));
        head.setCmd((byte) 1);

        // 组成完整协议对象
        Protocol protocol = new Protocol();
        protocol.setHead(head);
        protocol.setBody(student);

        // 序列化和反序列化
        byte[] bytes = MagicByte.unpackToByte(protocol);
        Protocol s = MagicByte.pack(bytes, Protocol.class);

        Assert.assertEquals(s.getHead(),head);
        Assert.assertEquals(s.getBody(),student);
        Assert.assertEquals(s.getHead().getCmd(),student.cmd());

        head.setCmd((byte) teacher.cmd());
        protocol.setBody(teacher);

        // 序列化和反序列化
        bytes = MagicByte.unpackToByte(protocol);
        s = MagicByte.pack(bytes, Protocol.class);

        Assert.assertEquals(s.getHead(),head);
        Assert.assertEquals(s.getBody(),teacher);
        Assert.assertEquals(s.getHead().getCmd(),teacher.cmd());

    }
}
