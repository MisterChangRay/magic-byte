package com.github.misterchangray.core.common;


import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.common.converter.entity.CustomConverterInField;
import com.github.misterchangray.core.common.converter.entity.CustomObj;
import com.github.misterchangray.core.common.converter.entity.SexEnum;
import com.github.misterchangray.core.enums.TypeEnum;
import org.junit.Assert;
import org.junit.Test;


/**
 * test simple data type
 * such as byte boolean char int short long float double
 *
 */
public class CustomConverterTest {


    /**
     * 测试在属性上使用自定义序列化注解
     */
    @Test
    public void testCustomConverterField() {
        CustomConverterInField customObj = new CustomConverterInField();
        customObj.setA(22);
        customObj.setB(SexEnum.MAN);
        customObj.setC(55);
        byte[] tmp = MagicByte.unpackToByte(customObj);
        CustomConverterInField pack = MagicByte.pack(tmp, CustomConverterInField.class);

        Assert.assertEquals(customObj.getA(), pack.getA());
        Assert.assertEquals(customObj.getB(), pack.getB());
        Assert.assertEquals(customObj.getB(), pack.getB());

        CustomConverterInField customObj2 = new CustomConverterInField();
        customObj2.setA(13);
        customObj2.setB(SexEnum.WOMAN);
        customObj2.setC(99);
        byte[] tmp2 = MagicByte.unpackToByte(customObj2);
        CustomConverterInField pack2 = MagicByte.pack(tmp2, CustomConverterInField.class);

        Assert.assertEquals(customObj2.getA(), pack2.getA());
        Assert.assertEquals(customObj2.getB(), pack2.getB());
        Assert.assertEquals(customObj2.getB(), pack2.getB());

    }

    /**
     * 测试在类上使用自定义序列化注解
     */
    @Test
    public void testCustomConverterObj() {
        CustomObj customObj = new CustomObj();
        customObj.setA(12);
        customObj.setB('s');
        byte[] tmp = MagicByte.unpackToByte(customObj);
        CustomObj pack = MagicByte.pack(tmp, CustomObj.class);

        Assert.assertEquals(customObj.getA(), pack.getA());
        Assert.assertEquals(customObj.getB(), pack.getB());

    }
}
