package com.github.misterchangray.core;

import com.github.misterchangray.core.util.ConverterUtil;
import org.junit.Assert;
import org.junit.Test;

public class UtilTest {



    @Test
    public void testByteToHexString() {
        byte[] a = new byte[] {0x1, 0x2, 0x3};
        Assert.assertEquals("010203", ConverterUtil.byteToHexString(a));
        a = new byte[] {0x10, 0x1a, 0x1b, 0x1c, 0x1d};
        Assert.assertEquals("101A1B1C1D", ConverterUtil.byteToHexString(a));
    }



    @Test
    public void testByteToUnsigned() {
        byte a = 12;
        Assert.assertEquals(12, ConverterUtil.byteToUnsigned(a));
        a = -12;
        Assert.assertEquals(244, ConverterUtil.byteToUnsigned(a));
    }

    @Test
    public void testHexStringToByte() {
        String a = "0x123";
        Assert.assertArrayEquals(new byte[]{1, 35}, ConverterUtil.hexStringToByte(a));

        a = "0X1234";
        Assert.assertArrayEquals(new byte[]{0x12, 0x34}, ConverterUtil.hexStringToByte(a));

        a = "9876";
        Assert.assertArrayEquals(new byte[]{(byte) 0x98, 0x76}, ConverterUtil.hexStringToByte(a));

        a = "876";
        Assert.assertArrayEquals(new byte[]{(byte) 0x8, 0x76}, ConverterUtil.hexStringToByte(a));
    }


    @Test
    public void testIntToHexString() {
        Assert.assertEquals("10", ConverterUtil.intToHexString(16));

        Assert.assertEquals("22b8", ConverterUtil.intToHexString(8888));

    }




}
