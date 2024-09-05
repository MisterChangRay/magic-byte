package com.github.misterchangray.core.byteorder;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.enums.ByteOrder;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

/**
 * 自定义端序测试类
 *
 * @author FULaBUla
 */
public class ByteOrderTest {

    /**
     * 测试属性使用不同端序的情况
     */
    @Test
    public void testBytOrder() {

        ByteOrderClass byteOrderClass = new ByteOrderClass();
        byteOrderClass.setA(1);
        byteOrderClass.setB(1);
        byteOrderClass.setC(Instant.parse("2024-01-01T00:00:00Z"));
        byteOrderClass.setD(1);
        byteOrderClass.setE(Instant.parse("2024-01-01T00:00:00Z"));
        byteOrderClass.setF(Instant.parse("2024-01-01T00:00:00Z"));
        byteOrderClass.setG(Instant.parse("2024-01-01T00:00:00Z"));

        MagicByte.configDefaultByteOrder(ByteOrder.BIG_ENDIAN);

        byte[] data1 = MagicByte.unpackToByte(byteOrderClass);
        ByteOrderClass byteOrderClass1 = MagicByte.pack(data1, ByteOrderClass.class);

        byte[] data2 = new byte[]{0, 0, 0, 1,
                1, 0, 0, 0,
                -128, 0, -110, 101,
                0, 0, 0, 1,
                0, 0, 0, 0, 101, -110, 0, -128,
                -128, 0, -110, 101, 0, 0,
                50, 48, 50, 52, 48, 49, 48, 49, 48, 56, 48, 48, 48, 48};

        Assert.assertArrayEquals(data2, data1);
        Assert.assertEquals(byteOrderClass, byteOrderClass1);


    }
}
