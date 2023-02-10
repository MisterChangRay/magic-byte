package com.github.misterchangray.core.util;

import org.junit.Assert;
import org.junit.Test;

public class ConverterUtilTest {

    @Test
    public void testNumberToByte() throws InterruptedException {
        for (int i = 0; i < (Integer.MAX_VALUE - 50); i+=10) {
            byte[] bytes = ConverterUtil.numberToByte(i, 8);
            long l = ConverterUtil.byteToNumber(bytes);
            Assert.assertEquals(i, l);
        }

    }




}