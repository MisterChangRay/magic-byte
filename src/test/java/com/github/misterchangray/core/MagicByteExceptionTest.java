package com.github.misterchangray.core;


import com.github.misterchangray.core.entity.*;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.util.CalcUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;

public class MagicByteExceptionTest {


    /**
     * 字符串超长后将会自动截取.
     *
     */
    @Test
    public void testArrayOrList() {
        ArrayEntity arrayEntity = new ArrayEntity();
        byte[] bytes = MagicByte.unpackToByte(arrayEntity);
        ArrayEntity pack = MagicByte.pack(bytes, ArrayEntity.class);
        Assert.assertArrayEquals(arrayEntity.getBytes(), pack.getBytes());

    }

    /**
     * 字符串超长后将会自动截取.
     *
     */
    @Test
    public void testNullOfString() {
        Phone p = new Phone();
        p.setPhone(183803802222L);
        p.setBrand(null);

        byte[] bytes = MagicByte.unpackToByte(p);
        Phone pack = MagicByte.pack(bytes, Phone.class);
        Assert.assertEquals(p.getPhone(), pack.getPhone());
        Assert.assertNull(p.getBrand());
        Assert.assertEquals(pack.getBrand(), "");

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
