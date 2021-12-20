package com.github.misterchangray.core;


import com.github.misterchangray.core.entity.AllDataTypes;
import com.github.misterchangray.core.entity.Student;
import com.github.misterchangray.core.entity.Teacher;
import com.github.misterchangray.core.simple.*;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


/**

 常规测试, 这里的数据都是完整的 符合定义标准的

 */
public class NormalTest {


    /**
     * test byte.
     *
     */
    @Test
    public void testStudent() {
        Teacher teacher = new Teacher();
        teacher.setName("teacher1");
        teacher.setId(22);
        teacher.setPhoneSize(3);
        teacher.setPhones(new long[teacher.getPhoneSize()]);
        for (int i = 0; i < teacher.getPhoneSize(); i++) {
            teacher.getPhones()[i] = 18300000 + i;
        }


        byte[] unpack = MagicByte.unpackToByte(teacher);
        Teacher pack = MagicByte.pack(unpack, Teacher.class);
        Assert.assertEquals(teacher.getId(), pack.getId());
        Assert.assertEquals(teacher.getName(), pack.getName());
        Assert.assertArrayEquals(teacher.getPhones(), pack.getPhones());
    }


}
