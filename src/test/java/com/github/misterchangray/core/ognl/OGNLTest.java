package com.github.misterchangray.core.ognl;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.ognl.pojo.Student;
import com.github.misterchangray.core.ognl.pojo.Teacher;
import org.junit.Assert;
import org.junit.Test;

public class OGNLTest {



    @Test
    public void testOgnl() {
        Student student = new Student();
        student.setId(5);
        student.setName("rayStudent");
        Teacher t = new Teacher();
        t.setName("ray");
        t.setId(2);
        t.setStudent(student);

        byte[] bytes = MagicByte.unpackToByte(t);


        Teacher t2 = MagicByte.pack(bytes, Teacher.class);
        // 这里序列化为字节时 id*3, 反序列化时再*3 所以=18
        Assert.assertEquals(t2.getId(), 18);
        Assert.assertEquals(t2.getStudent().getId(), 125);
        Assert.assertEquals(t2.getStuName(), t.getStudent().getName());


    }

}
