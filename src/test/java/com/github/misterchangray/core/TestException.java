package com.github.misterchangray.core;


import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.ClassParser;
import com.github.misterchangray.core.dynamic.DynamicClasses;
import com.github.misterchangray.core.dynamic.StrictDynamicClasses;
import com.github.misterchangray.core.entity.error.*;
import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.exception.InvalidTypeException;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.exception.OutOfMemoryDetecteException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class TestException {


    @Test
    public void testMagicParseException() throws InterruptedException {
        Assert.assertThrows(MagicParseException.class, () -> {
            StrictDynamicClasses classes1 = StrictDynamicClasses.build(1).get(0);

            ByteBuffer unpack = MagicByte.unpack(classes1);
            byte[] tmp = Arrays.copyOfRange(unpack.array(), 0, 100);
            StrictDynamicClasses pack = MagicByte.pack(tmp, StrictDynamicClasses.class);

        });
    }

    @Test
    public void testOOM2() throws InterruptedException {
        Assert.assertThrows(OutOfMemoryDetecteException.class, () -> {
            TestLargeString classes1 = new TestLargeString();

            ByteBuffer unpack = MagicByte.unpack(classes1);
            byte[] tmp = Arrays.copyOfRange(unpack.array(), 0, 100);
            StrictDynamicClasses pack = MagicByte.pack(tmp, StrictDynamicClasses.class);

        });
    }



    @Test
    public void testOOM() throws InterruptedException {
        Assert.assertThrows(OutOfMemoryDetecteException.class, () -> {
            DynamicClasses classes1 = DynamicClasses.build(1).get(0);

            ByteBuffer unpack = MagicByte.unpack(classes1);
            unpack.position(0);
            unpack.putInt(0xff);
            DynamicClasses pack = MagicByte.pack(unpack.array(), DynamicClasses.class);

        });
    }


    /**
     * dynamicSize of 只能标记在 list array string 上.
     *
     */
    @Test
    public void TestArrayString() {
        TestArrayString testSameOrder = new TestArrayString();
        Assert.assertThrows(InvalidTypeException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(testSameOrder);
        });
    }

    /**
     * dynamicSize of 只能标记在 list array string 上.
     *
     */
    @Test
    public void TestListString() {
        TestListString testSameOrder = new TestListString();
        Assert.assertThrows(InvalidTypeException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(testSameOrder);
        });
    }

    /**
     * size or dynamicSize only use one
     *
     */
    @Test
    public void testList2() {
        TestList2 testSameOrder = new TestList2();
        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(testSameOrder);
        });
    }

    /**
     * list string 必须配置size 或者dynamicsize
     *
     */
    @Test
    public void testList() {
        TestList testSameOrder = new TestList();
        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(testSameOrder);
        });
    }



    /**
     * dynamicSize of 只能标记在 list array string 上.
     *
     */
    @Test
    public void testDynamicSizeOf3() {
        TestDynamicSizeOf3 testSameOrder = new TestDynamicSizeOf3();
        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(testSameOrder);
        });
    }


    /**
     * dynamicSize of 只能标记在 list array string 上.
     *
     */
    @Test
    public void testDynamicSizeOf2() {
        TestDynamicSizeOf2 testSameOrder = new TestDynamicSizeOf2();
        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(testSameOrder);
        });
    }

    /**
     * dynamicSize of 只能标记在 list array string 上.
     *
     */
    @Test
    public void testDynamicSizeOf() {
        TestDynamicSizeOf testSameOrder = new TestDynamicSizeOf();
        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(testSameOrder);
        });
    }


    /**
     * 测试数组必须大于声明.
     *
     */
    @Test
    public void testSameOrder() {
        TestSameOrder testSameOrder = new TestSameOrder();
        testSameOrder.setA(1);
        testSameOrder.setB(2);
        testSameOrder.setC(3);
        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(testSameOrder);
        });
    }


    /**
     * 测试数组必须大于声明.
     *
     */
    @Test
    public void TestListMatrix() {
        ClassParser classParser = new ClassParser();

        Assert.assertThrows(InvalidTypeException.class, () -> {
            ClassMetaInfo parse = classParser.parse(TestListMatrix.class);
        });
    }

    @Test
    public void TestArrayMatrix() {
        ClassParser classParser = new ClassParser();
        Assert.assertThrows(InvalidTypeException.class, () -> {
            ClassMetaInfo parse = classParser.parse(TestArrayMatrix.class);
        });
    }

}
