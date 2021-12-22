package com.github.misterchangray.core;


import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.ClassParser;
import com.github.misterchangray.core.entity.error.*;
import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.exception.InvalidTypeException;
import com.github.misterchangray.core.exception.MagicParseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class TestException {


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
     * dynamicSize of 只能标记在 list array string 上.
     *
     */
    @Test
    public void testDynamicSizeOf2() {
        TestDynamicSizeOf2 testSameOrder = new TestDynamicSizeOf2();
        Assert.assertThrows(InvalidTypeException.class, () -> {
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
        Assert.assertThrows(MagicParseException.class, () -> {
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

        Assert.assertThrows(MagicParseException.class, () -> {
            ClassMetaInfo parse = classParser.parse(TestListMatrix.class);
        });
    }

    @Test
    public void TestArrayMatrix() {
        ClassParser classParser = new ClassParser();
        Assert.assertThrows(MagicParseException.class, () -> {
            ClassMetaInfo parse = classParser.parse(TestArrayMatrix.class);
        });
    }

}
