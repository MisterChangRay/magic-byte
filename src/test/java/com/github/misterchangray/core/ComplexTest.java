package com.github.misterchangray.core;


import com.github.misterchangray.core.entity.Student;
import com.github.misterchangray.core.entity.custom.UnknownType;
import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {

    /**
     * test for array byte fill.
     *
     */
    @Test
    public void testArrayFill() throws InterruptedException {
        Student student = Student.build(1).get(0);
        student.setBookids(new int[]{22});

        byte[] bytes = MagicByte.unpackToByte(student);
        Student pack = MagicByte.pack(bytes, Student.class);

        Assert.assertEquals(student.getPhone(), pack.getPhone());

    }


    /**
     * 未知数据类型.
     *
     */
    @Test
    public void testUnknownType() throws InterruptedException {
        UnknownType unknownType = UnknownType.build(2).get(0);
        byte[] bytes = MagicByte.unpackToByte(unknownType);
        UnknownType pack = MagicByte.pack(bytes, UnknownType.class);

        Assert.assertNull(pack.getBrithday());
        Assert.assertNull(pack.getAttr());

    }


}
