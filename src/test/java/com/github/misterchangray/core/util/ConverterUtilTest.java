package com.github.misterchangray.core.util;

import com.github.misterchangray.core.clazz.warpper.ULong;
import com.github.misterchangray.core.clazz.warpper.UNumber;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * 工具类的单元测试用例
 */
public class ConverterUtilTest {

    @Test
    public void testNumberToByte() throws InterruptedException {
        for (int i = 0; i < (Integer.MAX_VALUE - 50); i+=10) {
            byte[] bytes = ConverterUtil.numberToByte(i);
            long l = ConverterUtil.byteToNumber(bytes);
            Assert.assertEquals(i, l);
        }

    }
    @Test
    public void testBigInteger() throws InterruptedException {
        for (int i = 0; i < (Integer.MAX_VALUE - 500); i+=100) {
            BigInteger s = BigInteger.valueOf(i);

            byte[] bytes = ConverterUtil.bigIntegerToByte(s);
            BigInteger l = ConverterUtil.byteToBigInteger(bytes);
            Assert.assertEquals(s, l);
        }

    }




}