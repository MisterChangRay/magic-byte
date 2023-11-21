package com.github.misterchangray.core.messager;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.example.HeartbeatCmd;
import com.github.misterchangray.core.intf.MagicMessage;
import com.github.misterchangray.core.messager.po.Head;
import com.github.misterchangray.core.messager.po.Student;
import com.github.misterchangray.core.messager.po.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class TestMessager {
    @Test
    public void testStudent() {
        MagicByte.registerCMD(Student.class);
        Student student = new Student();
        student.setAge(13);
        student.setName("AA");

        byte[] bytes = MagicByte.unpackToByte(student);
        MagicMessage s1 = MagicByte.pack(bytes);

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
        MagicByte.registerCMD(34, Teacher.class);
        Head head = new Head();
        head.setLen(UInt.valueOf(17));
        Teacher teacher = new Teacher();
        teacher.setHead(head);
        teacher.setAge(38);
        teacher.setName("EABC");

        byte[] bytes = MagicByte.unpackToByte(teacher);
        MagicMessage s1 = MagicByte.pack(bytes);

        Assert.assertEquals(s1.cmd(), 34);
        Teacher s  = (Teacher)s1;
        Assert.assertEquals(s.getHead().getLen(), teacher.getHead().getLen());
        Assert.assertEquals(s.getHead().getCmd(),teacher.getHead().getCmd());
        Assert.assertEquals(s.getAge(),teacher.getAge());
        Assert.assertEquals(s.getName(),teacher.getName());
    }

}
