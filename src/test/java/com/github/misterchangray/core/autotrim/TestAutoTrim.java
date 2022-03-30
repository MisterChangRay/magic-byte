package com.github.misterchangray.core.autotrim;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.autotrim.pojo.*;
import com.github.misterchangray.core.common.dynamic.DynamicStudent;
import com.github.misterchangray.core.common.simple.ByteObj;
import com.github.misterchangray.core.exception.InvalidParameterException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TestAutoTrim {

    @Test
    public void testAutoTrimNesting() throws InterruptedException {

        AutoTrimNesting autoTrimNesting = new AutoTrimNesting();

        autoTrimNesting.setName("ff");

        AutoTrimArray autoTrimArray = new AutoTrimArray();
        autoTrimArray.setName("www");
        autoTrimArray.setBoodsId(new int[]{3,4});
        autoTrimArray.setAge(66);

        autoTrimNesting.setAutoTrimArray(autoTrimArray);


        ByteBuffer unpack = MagicByte.unpack(autoTrimNesting);
        AutoTrimNesting pack = MagicByte.pack(unpack.array(), AutoTrimNesting.class);
        Assert.assertEquals(autoTrimNesting.getName(), pack.getName());
        Assert.assertArrayEquals(autoTrimArray.getBoodsId(), pack.getAutoTrimArray().getBoodsId());
        Assert.assertEquals(autoTrimArray.getAge(), pack.getAutoTrimArray().getAge());
        Assert.assertEquals(autoTrimArray.getName(), pack.getAutoTrimArray().getName());
    }


    @Test
    public void testAutoTrimObjWithDynamic() throws InterruptedException {
        AutoTrimObjWithDynamic autoTrimObjWithDynamic = new AutoTrimObjWithDynamic();
        autoTrimObjWithDynamic.setName("fffd");
        ArrayList<ByteObj> arrayList = new ArrayList();

        for (int i = 0; i < 3; i++) {
            ByteObj byteObj = new ByteObj();
            byteObj.setA((byte) i);
            byteObj.setB((byte) (i + 10));
            arrayList.add(byteObj);
        }

        Assert.assertThrows(InvalidParameterException.class, ()-> {
            autoTrimObjWithDynamic.setBoodsId(arrayList);
            autoTrimObjWithDynamic.setStudent(DynamicStudent.build(3).get(1));
            ByteBuffer unpack = MagicByte.unpack(autoTrimObjWithDynamic);
            AutoTrimObjWithDynamic pack = MagicByte.pack(unpack.array(), AutoTrimObjWithDynamic.class);
        });


    }

    /**
     * autoTrim 只能使用 string 和 数组 成员
     * @throws InterruptedException
     */
    @Test
    public void testAutoTrimObj() throws InterruptedException {
        AutoTrimObj classes1 = new AutoTrimObj();
        classes1.setAge(12);
        classes1.setName("fuck");

        ArrayList<ByteObj> arrayList = new ArrayList();

        for (int i = 0; i < 3; i++) {
            ByteObj byteObj = new ByteObj();
            byteObj.setA((byte) i);
            byteObj.setB((byte) (i + 10));
            arrayList.add(byteObj);
        }
        classes1.setBoodsId(arrayList);

        ByteBuffer unpack = MagicByte.unpack(classes1);
        AutoTrimObj pack = MagicByte.pack(unpack.array(), AutoTrimObj.class);

        Assert.assertEquals(classes1.getAge(), pack.getAge());
        Assert.assertEquals(classes1.getName(), pack.getName());
        for (int i = 0; i < classes1.getBoodsId().size(); i++) {
            ByteObj byteObj = classes1.getBoodsId().get(i);
            ByteObj byteObj1 = pack.getBoodsId().get(i);
            Assert.assertEquals(byteObj.getA(), byteObj1.getA());
            Assert.assertEquals(byteObj.getB(), byteObj1.getB());
        }
    }


    /**
     * autoTrim 只能使用 string 和 数组 成员
     * @throws InterruptedException
     */
    @Test
    public void testAutoTrimArray() throws InterruptedException {
        AutoTrimArray classes1 = new AutoTrimArray();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        ByteBuffer unpack = MagicByte.unpack(classes1);
        AutoTrimArray pack = MagicByte.pack(unpack.array(), AutoTrimArray.class);

        Assert.assertEquals(classes1.getAge(), pack.getAge());
        Assert.assertEquals(classes1.getName(), pack.getName());
        Assert.assertArrayEquals(classes1.getBoodsId(), pack.getBoodsId());
    }

    /**
     * autoTrim 字符串使用
     * @throws InterruptedException
     */
    @Test
    public void testAutoTrimString() throws InterruptedException {
        AutoTrimString classes1 = new AutoTrimString();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        ByteBuffer unpack = MagicByte.unpack(classes1);
        AutoTrimString pack = MagicByte.pack(unpack.array(), AutoTrimString.class);

        Assert.assertEquals(classes1.getAge(), pack.getAge());
        Assert.assertEquals(classes1.getName(), pack.getName());
        Assert.assertArrayEquals(classes1.getBoodsId(), pack.getBoodsId());


    }


    /**
     * autoTrim 只能使用 string 和 数组 成员
     * @throws InterruptedException
     */
    @Test
    public void testAutoTrimUseErrorType() throws InterruptedException {
        AutoTrimUseErrorType classes1 = new AutoTrimUseErrorType();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(classes1);
            AutoTrimUseErrorType pack = MagicByte.pack(unpack.array(), AutoTrimUseErrorType.class);
        });
    }


    /**
     * 单类使用 autotrim 一次以上
     * @throws InterruptedException
     */
    @Test
    public void testAutoTrimWith2Field() throws InterruptedException {
        AutoTrimWith2Field classes1 = new AutoTrimWith2Field();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(classes1);
            AutoTrimWith2Field pack = MagicByte.pack(unpack.array(), AutoTrimWith2Field.class);
        });
    }

    /**
     * 测试autotrim 搭配dynamicOf使用
     * @throws InterruptedException
     */
    @Test
    public void testAutoTrimWithDynamicSize() throws InterruptedException {
        AutoTrimWithDynamicSize classes1 = new AutoTrimWithDynamicSize();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(classes1);
            AutoTrimWithDynamicSize pack = MagicByte.pack(unpack.array(), AutoTrimWithDynamicSize.class);
        });
    }


    /**
     * 测试autotrim 没有配置size 属性
     * @throws InterruptedException
     */
    @Test
    public void testAutoTrimWithoutSize() throws InterruptedException {
        AutoTrimWithoutSize classes1 = new AutoTrimWithoutSize();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(classes1);
            AutoTrimWithoutSize pack = MagicByte.pack(unpack.array(), AutoTrimWithoutSize.class);
        });
    }


}
