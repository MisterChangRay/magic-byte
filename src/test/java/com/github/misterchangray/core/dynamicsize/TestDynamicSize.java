package com.github.misterchangray.core.dynamicsize;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.warpper.UByte;
import com.github.misterchangray.core.dynamicsize.pojo.*;
import com.github.misterchangray.core.common.dynamic.DynamicStudent;
import com.github.misterchangray.core.common.simple.ByteObj;
import com.github.misterchangray.core.dynamicsize.pojo.error.DynamicSizeDuplicatedId;
import com.github.misterchangray.core.dynamicsize.pojo.error.SimpleDuplicatedId;
import com.github.misterchangray.core.dynamicsize.pojo.nested.DynamicHead;
import com.github.misterchangray.core.dynamicsize.pojo.nested.DynamicHead2;
import com.github.misterchangray.core.dynamicsize.pojo.nested.DynamicSizeFromId;
import com.github.misterchangray.core.exception.InvalidParameterException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TestDynamicSize {

    /**
     * 测试 dynamicSizeOf 为嵌套对象
     *
     * 这里会找不到指定的属性
     * 
     * @throws InterruptedException
     */
    @Test
    public void testSimpleDuplicateIdNested() throws InterruptedException {
        DynamicSizeDuplicatedId simpleDuplicatedId = new DynamicSizeDuplicatedId();
        Assert.assertThrows(InvalidParameterException.class , () -> {
            MagicByte.unpack(simpleDuplicatedId);
        });
    }


    /**
     * 测试 dynamicSizeOf 为嵌套对象
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeLenNesting() throws InterruptedException {
        DynamicHead2 dynamicHead2 = new DynamicHead2();
        dynamicHead2.setLen(UByte.valueOf(3));
        dynamicHead2.setFu("w3");
        DynamicHead dynamicHead = new DynamicHead();
        dynamicHead.setLen((short) 2);
        dynamicHead.setHead2(dynamicHead2);


        DynamicSizeFromId dynamicSizeFromId = new DynamicSizeFromId();
        dynamicSizeFromId.setAge(33);
        dynamicSizeFromId.setName("f33");
        dynamicSizeFromId.setHead(dynamicHead);
        dynamicSizeFromId.setBoodsId(new int[]{11,22,33,44,55});

        byte[] bytes = MagicByte.unpackToByte(dynamicSizeFromId);
        DynamicSizeFromId pack = MagicByte.pack(bytes, DynamicSizeFromId.class);

        Assert.assertEquals(pack.getAge(), dynamicSizeFromId.getAge());
        Assert.assertEquals(pack.getHead().getLen(), dynamicSizeFromId.getHead().getLen());
        Assert.assertEquals(pack.getBoodsId().length, dynamicSizeFromId.getHead().getLen());
        Assert.assertEquals(pack.getHead().getHead2().getFu(), dynamicSizeFromId.getHead().getHead2().getFu());
        Assert.assertEquals(pack.getHead().getHead2().getLen(), dynamicSizeFromId.getHead().getHead2().getLen());
    }


    /**
     * 测试 dynamicSize 嵌套使用是否正常
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeNesting() throws InterruptedException {

        DynamicSizeNesting dynamicSizeNesting = new DynamicSizeNesting();

        dynamicSizeNesting.setName("ff");

        DynamicSizeArray dynamicSizeArray = new DynamicSizeArray();
        dynamicSizeArray.setName("www");
        dynamicSizeArray.setBoodsId(new int[]{3,4});
        dynamicSizeArray.setAge(66);

        dynamicSizeNesting.setDynamicSizeArray(dynamicSizeArray);


        ByteBuffer unpack = MagicByte.unpack(dynamicSizeNesting);
        DynamicSizeNesting pack = MagicByte.pack(unpack.array(), DynamicSizeNesting.class);
        Assert.assertEquals(dynamicSizeNesting.getName(), pack.getName());
        Assert.assertArrayEquals(dynamicSizeArray.getBoodsId(), pack.getDynamicSizeArray().getBoodsId());
        Assert.assertEquals(dynamicSizeArray.getAge(), pack.getDynamicSizeArray().getAge());
        Assert.assertEquals(dynamicSizeArray.getName(), pack.getDynamicSizeArray().getName());
    }


    /**
     * 测试 dynamicSize 和 dynamic 同时使用
     * 应该抛出异常 InvalidParameterException
     *
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeObjWithDynamic() throws InterruptedException {
        DynamicSizeObjWithDynamic dynamicSizeObjWithDynamic = new DynamicSizeObjWithDynamic();
        dynamicSizeObjWithDynamic.setName("fffd");
        ArrayList<ByteObj> arrayList = new ArrayList();

        for (int i = 0; i < 3; i++) {
            ByteObj byteObj = new ByteObj();
            byteObj.setA((byte) i);
            byteObj.setB((byte) (i + 10));
            arrayList.add(byteObj);
        }

        Assert.assertThrows(InvalidParameterException.class, ()-> {
            dynamicSizeObjWithDynamic.setBoodsId(arrayList);
            dynamicSizeObjWithDynamic.setStudent(DynamicStudent.build(3).get(1));
            ByteBuffer unpack = MagicByte.unpack(dynamicSizeObjWithDynamic);
            DynamicSizeObjWithDynamic pack = MagicByte.pack(unpack.array(), DynamicSizeObjWithDynamic.class);
        });


    }

    /**
     * 测试 dynamicSize 在数组使用
     * dynamicSize 只能使用 string 和 数组 成员
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeObj() throws InterruptedException {
        DynamicSizeObj classes1 = new DynamicSizeObj();
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
        DynamicSizeObj pack = MagicByte.pack(unpack.array(), DynamicSizeObj.class);

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
     * 测试 dynamicSize 在数组中使用
     * dynamicSize 只能使用 string 和 数组 成员
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeArray() throws InterruptedException {
        DynamicSizeArray classes1 = new DynamicSizeArray();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        ByteBuffer unpack = MagicByte.unpack(classes1);
        DynamicSizeArray pack = MagicByte.pack(unpack.array(), DynamicSizeArray.class);

        Assert.assertEquals(classes1.getAge(), pack.getAge());
        Assert.assertEquals(classes1.getName(), pack.getName());
        Assert.assertArrayEquals(classes1.getBoodsId(), pack.getBoodsId());
    }

    /**
     * dynamicSize 字符串使用
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeString() throws InterruptedException {
        DynamicSizeString classes1 = new DynamicSizeString();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        ByteBuffer unpack = MagicByte.unpack(classes1);
        DynamicSizeString pack = MagicByte.pack(unpack.array(), DynamicSizeString.class);

        Assert.assertEquals(classes1.getAge(), pack.getAge());
        Assert.assertEquals(classes1.getName(), pack.getName());
        Assert.assertArrayEquals(classes1.getBoodsId(), pack.getBoodsId());


    }


    /**
     * 测试dynamicSize 在错误的数据类型上使用 ， 应该抛出异常 InvalidParameterException
     * dynamicSize 只能使用 string 和 数组 成员
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeUseErrorType() throws InterruptedException {
        DynamicSizeUseErrorType classes1 = new DynamicSizeUseErrorType();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(classes1);
            DynamicSizeUseErrorType pack = MagicByte.pack(unpack.array(), DynamicSizeUseErrorType.class);
        });
    }


    /**
     *
     * 单类使用 dynamicSize 一次以上； 应抛出异常 InvalidParameterException
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeWith2Field() throws InterruptedException {
        DynamicSizeWith2Field classes1 = new DynamicSizeWith2Field();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(classes1);
            DynamicSizeWith2Field pack = MagicByte.pack(unpack.array(), DynamicSizeWith2Field.class);
        });
    }

    /**
     * 测试 dynamicSize 搭配dynamicOf使用， 应该抛出异常
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeWithDynamicSize() throws InterruptedException {
        DynamicSizeWithDynamicSize classes1 = new DynamicSizeWithDynamicSize();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(classes1);
            DynamicSizeWithDynamicSize pack = MagicByte.pack(unpack.array(), DynamicSizeWithDynamicSize.class);
        });
    }


    /**
     * dynamicSize 没有配置size 属性, 应该抛出异常 InvalidParameterException
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeWithoutSize() throws InterruptedException {
        DynamicSizeWithoutSize classes1 = new DynamicSizeWithoutSize();
        classes1.setAge(12);
        classes1.setName("fuck");
        classes1.setBoodsId(new int[]{3, 4, 5});

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(classes1);
            DynamicSizeWithoutSize pack = MagicByte.pack(unpack.array(), DynamicSizeWithoutSize.class);
        });
    }


}
