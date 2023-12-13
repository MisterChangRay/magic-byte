package com.github.misterchangray.core.bugtest.for47;

import com.github.misterchangray.core.MagicByte;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;


/**
 *
 * 详情
 * 参阅github bug#47
 */

public class TestForBug47 {
    @Test
    public void testDynamicSizeWithConvert() {
        System.out.println(1);
        MagicByte.configDefaultCharset(StandardCharsets.UTF_8);
        A a = new A();
        a.setId(1);
        a.setB("1");
        byte[] data = MagicByte.unpackToByte(a);
        A a1 = MagicByte.pack(data, A.class);
        Assert.assertEquals(a, a1);

        B b = new B();
        b.setA(a);
        b.setData("你好".getBytes());
        b.setCheck(new byte[4]);

        data = MagicByte.unpackToByte(b);
        B b1 = MagicByte.pack(data, B.class);
        Assert.assertEquals(b, b1);

    }


    }


