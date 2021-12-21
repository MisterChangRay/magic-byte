package com.github.misterchangray.core;


import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.ClassParser;
import com.github.misterchangray.core.entity.error.TestArrayMatrix;
import com.github.misterchangray.core.entity.error.TestListMatrix;
import com.github.misterchangray.core.entity.error.TestSameOrder;
import com.github.misterchangray.core.exception.MagicParseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class TestException {


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
