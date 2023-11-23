package com.github.misterchangray.core.messager;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.intf.MagicMessage;
import com.github.misterchangray.core.messager.po.Head;
import com.github.misterchangray.core.messager.po2.CmdFieldduplicateDefinedObj;
import com.github.misterchangray.core.messager.po2.DiffentCmdFieldObj;
import com.github.misterchangray.core.messager.po2.NoCmdFieldObj;
import com.github.misterchangray.core.messager.po.Student;
import com.github.misterchangray.core.messager.po.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class TestMessager {
    @Test
    public void testStudent() {
        // 注册消息命令,此消息类型写死到类中
        MagicByte.registerCMD(Student.class);
        Student student = new Student();
        student.setAge(13);
        student.setName("AA");

        byte[] bytes = MagicByte.unpackToByte(student);
        MagicMessage s1 = MagicByte.pack(bytes);

        // 可以通过 cmd() 函数获取当前消息类型, 前提是必须要实现接口方法
        Assert.assertEquals(s1.cmd(), 33);
        Student s = (Student) s1;

        Assert.assertEquals(s.getHead(),student.getHead());
        Assert.assertNotEquals(s.getLen(),student.getLen());
        Assert.assertEquals(s.getCmd(),student.getCmd());
        Assert.assertEquals(s.getAge(),student.getAge());
        Assert.assertEquals(s.getName(),student.getName());
    }

    @Test
    public void testTeacher() {
        // 注册消息,由于使用了公共头,所以注册时需要指定消息类型
        MagicByte.registerCMD(34, Teacher.class);
        Head head = new Head();
        head.setLen(UInt.valueOf(17));
        Teacher teacher = new Teacher();
        teacher.setHead(head);
        teacher.setAge(38);
        teacher.setName("EABC");

        byte[] bytes = MagicByte.unpackToByte(teacher);
        MagicMessage s1 = MagicByte.pack(bytes);

        // 可以通过 cmd() 函数获取当前消息类型, 前提是必须要实现接口方法
        Assert.assertEquals(s1.cmd(), 34);
        Teacher s  = (Teacher)s1;
        Assert.assertEquals(s.getHead().getLen(), teacher.getHead().getLen());
        Assert.assertEquals(s.getHead().getCmd(),teacher.getHead().getCmd());
        Assert.assertEquals(s.getAge(),teacher.getAge());
        Assert.assertEquals(s.getName(),teacher.getName());
    }

    /**
     * 测试没有申明cmd属性注册消息时将会抛出异常
     */
    @Test
    public void testCmdFiledChecker() {
        Assert.assertThrows(InvalidParameterException.class, () -> {
            MagicByte.registerCMD(NoCmdFieldObj.class);
        });

    }
    /**
     * 测试重复申明cmd属性
     * 注册消息时将会抛出异常
     */
    @Test
    public void testCmdFiledChecker2() {
        Assert.assertThrows(InvalidParameterException.class, () -> {
            MagicByte.registerCMD(CmdFieldduplicateDefinedObj.class);
        });

    }

    /**
     * 测试注册注册消息时使用不同长度的字节数来申明cmd属性
     * 此时注册消息时将会抛出异常
     * 同一个应用内的所有消息以下应该保持一致：
     * 1. 消息类型字段偏移量
     * 2. 消息类型字段的字节数
     */
    @Test
    public void testCmdFiledChecker3() {
        Assert.assertThrows(InvalidParameterException.class, () -> {
            MagicByte.registerCMD(34, Teacher.class);
            MagicByte.registerCMD(DiffentCmdFieldObj.class);
        });

    }

    /**
     * 注册相同消息将会抛出异常
     */
    @Test
    public void testCmdFiledChecker4() {
        Assert.assertThrows(InvalidParameterException.class, () -> {
            MagicByte.registerCMD(34, Teacher.class);
            MagicByte.registerCMD(34, Teacher.class);
        });

    }
}
