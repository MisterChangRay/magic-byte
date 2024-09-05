package com.github.misterchangray.core.byteorder;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.enums.ByteOrder;
import org.junit.Assert;
import org.junit.Test;

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

        MagicByte.configDefaultByteOrder(ByteOrder.BIG_ENDIAN);

        byte[] data1 = MagicByte.unpackToByte(byteOrderClass);
        ByteOrderClass byteOrderClass1 = MagicByte.pack(data1, ByteOrderClass.class);

        byte[] data2 = new byte[]{0, 0, 0, 1, 1, 0, 0, 0};

        Assert.assertEquals(byteOrderClass, byteOrderClass1);
        Assert.assertArrayEquals(data2, data1);
    }
}
