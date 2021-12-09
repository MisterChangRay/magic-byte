package com.github.misterchangray.core;


import com.github.misterchangray.core.entity.*;
import com.github.misterchangray.core.exception.MagicCollectionSizeNotMatchException;
import com.github.misterchangray.core.exception.MagicUnpackValueNotBeNullException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class MagicByteExceptionTest {


    /**
     * 测试数组必须大于声明.
     *
     */
    @Test
    public void testSizeNotMatch() {
        Assert.<MagicCollectionSizeNotMatchException>assertThrows(MagicCollectionSizeNotMatchException.class, ()-> {
            Student student = new Student();
            student.setPhones(new long[]{1,2,3});
            student.setBookIds(new byte[]{1,2,3,4});
            student.setAge(123);
            student.setName("xd");
            student.setAge(11);
            ByteBuffer unpack = MagicByte.unpack(student);
        });


    }

    /**
     * 字符串超长后将会自动截取.
     *
     */
    @Test
    public void testNotBeNull() {
        Assert.<MagicUnpackValueNotBeNullException>assertThrows(MagicUnpackValueNotBeNullException.class, ()-> {
            ArrayEntity arrayEntity = new ArrayEntity();
            byte[] bytes = MagicByte.unpackToByte(arrayEntity);
            ArrayEntity pack = MagicByte.pack(bytes, ArrayEntity.class);
            Assert.assertArrayEquals(arrayEntity.getBytes(), pack.getBytes());

        });

    }

    /**
     * 字符串超长后将会自动截取.
     *
     */
    @Test
    public void testNullOfString() {
        Phone p = new Phone();
        p.setPhone(183803802222L);
        p.setBrand("null");

        byte[] bytes = MagicByte.unpackToByte(p);
        Phone pack = MagicByte.pack(bytes, Phone.class);
        Assert.assertEquals(p.getPhone(), pack.getPhone());
        Assert.assertEquals(pack.getBrand(), "null");

    }


    /**
     * 字符串超长后将会自动截取.
     *
     */
    @Test
    public void testStringOverflow() {
        Phone p = new Phone();
        p.setPhone(183803802222L);
        p.setBrand("thisIsAOverflowStrings");

        byte[] bytes = MagicByte.unpackToByte(p);
        Phone pack = MagicByte.pack(bytes, Phone.class);
        Assert.assertEquals(p.getPhone(), pack.getPhone());
        Assert.assertNotEquals(p.getBrand(), pack.getBrand());
        Assert.assertTrue(p.getBrand().length() > pack.getBrand().length());


        p.setPhone(183803802222L);
        p.setBrand("thisIs");
        bytes = MagicByte.unpackToByte(p);
        pack = MagicByte.pack(bytes, Phone.class);
        Assert.assertEquals(p.getPhone(), pack.getPhone());
        Assert.assertEquals(p.getBrand(), pack.getBrand());

    }

    /**
     * 字符串超长后将会自动截取.
     *
     */
    @Test
    public void testDynamicString() {

        // dynamic 未设置长度时， 按照实际长度为准
        DynamicString dynamicString = new DynamicString();
        dynamicString.setEmail("misterchagnray@hotmail.com");
        dynamicString.setName("misterchagnray@hotmail.com");
        dynamicString.setDate(1111111111L);

        byte[] bytes = MagicByte.unpackToByte(dynamicString);
        DynamicString pack = MagicByte.pack(bytes, DynamicString.class);
        Assert.assertEquals(dynamicString.getDate(), pack.getDate());
        Assert.assertEquals(dynamicString.getLen().intValue(), dynamicString.getEmail().length());
        Assert.assertEquals(dynamicString.getLen(), pack.getLen());
        Assert.assertEquals(dynamicString.getEmail(), pack.getEmail());
        Assert.assertNotEquals(dynamicString.getName(), pack.getName());
        Assert.assertTrue(dynamicString.getName().length() > pack.getName().length());

        // dynamic 设置长度后, 以设置的长度为准
        dynamicString.setLen(12);
        bytes = MagicByte.unpackToByte(dynamicString);
        pack = MagicByte.pack(bytes, DynamicString.class);

        Assert.assertEquals(dynamicString.getLen().intValue(), dynamicString.getLen().intValue());
        Assert.assertNotEquals(dynamicString.getEmail(), pack.getEmail());

    }

}
