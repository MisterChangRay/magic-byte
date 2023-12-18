package com.github.misterchangray.core.dynamicsize;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.clazz.warpper.UByte;
import com.github.misterchangray.core.common.dynamic.DynamicStudent;
import com.github.misterchangray.core.common.simple.ByteObj;
import com.github.misterchangray.core.dynamicsize.pojo.*;
import com.github.misterchangray.core.dynamicsize.pojo.error.DynamicSizeDuplicatedId;
import com.github.misterchangray.core.dynamicsize.pojo.nested.DynamicHead;
import com.github.misterchangray.core.dynamicsize.pojo.nested.DynamicHead2;
import com.github.misterchangray.core.dynamicsize.pojo.nested.DynamicSizeFromId;
import com.github.misterchangray.core.dynamicsize.pojo.nested2.DynamicSizeBox;
import com.github.misterchangray.core.dynamicsize.pojo.nested2.HeadBox1;
import com.github.misterchangray.core.dynamicsize.pojo.nested2.HeadBox2;
import com.github.misterchangray.core.dynamicsize.pojo.nested3.Box1;
import com.github.misterchangray.core.dynamicsize.pojo.nested3.Box2;
import com.github.misterchangray.core.dynamicsize.pojo.nested3.Box3;
import com.github.misterchangray.core.dynamicsize.pojo.nested3.BoxRoot;
import com.github.misterchangray.core.dynamicsize.pojo.nested4.Box41;
import com.github.misterchangray.core.dynamicsize.pojo.nested4.BoxRoot4;
import com.github.misterchangray.core.exception.InvalidParameterException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TestDynamicSize {
    /**
     * 测试跨级出现相同length
     *
     *
     * @throws InterruptedException
     */
    @Test
    public void testBoxRoot4() throws InterruptedException {

        BoxRoot4 a  = new BoxRoot4();
        Box41 b = new Box41();
        b.data = new byte[]{1,2,3,4};
        b.length = 2;
        List<Box41> objects = new ArrayList<>();
        objects.add(b);

        a.length = 3;
        a.value = "abcdefh";
        a.dataLength = 1;
        a.data = objects;

        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(BoxRoot4.class);

        for (FieldMetaInfo flatField : classMetaInfo.getFlatFields()) {
            Assert.assertEquals(flatField.getAccessPath(), flatField.getField().getName());
        }
        byte[] bytes = MagicByte.unpackToByte(a);
        BoxRoot4 pack = MagicByte.pack(bytes, BoxRoot4.class);
        Assert.assertEquals(bytes.length, 17);
        Assert.assertEquals(pack.length, 3);
        Assert.assertEquals(pack.value, "abc");
        Assert.assertEquals(pack.dataLength, 1);
        Assert.assertEquals(pack.data.get(0).length, a.data.get(0).length);
        Assert.assertArrayEquals(pack.data.get(0).data,  new byte[]{1,2});


    }



    /**
     * 测试 dynamicSizeOf 为嵌套对象
     *
     * 更复杂的测试相对引用和绝对引用
     *
     * @throws InterruptedException
     */
    @Test
    public void testBoxRoot() throws InterruptedException {
        Box1 box1= new Box1();
        box1.setLenStr("qwertyuio");
        box1.setLen2Str("asdfghjkl");
        box1.setLen(UByte.valueOf(6));


        Box3 box3 = new Box3();
        box3.setParentlen("!@#$%^^&*(");
        box3.setLen(UByte.valueOf(5));

        Box2 box2= new Box2();
        box2.setBox3(box3);
        box2.setParentlen("zxcvbnm<>");
        box2.setLen(UByte.valueOf(4));
        box2.setBox3Str("987654321");

        BoxRoot boxRoot = new BoxRoot();
        boxRoot.setBox1(box1);
        boxRoot.setBox2(box2);
        boxRoot.setBox2_box3lenStr("abcdefghijk");
        boxRoot.setLen((byte) 2);
        boxRoot.setRootLenStr("MNBHJKIU");

        byte[] bytes = MagicByte.unpackToByte(boxRoot);
        BoxRoot pack = MagicByte.pack(bytes, BoxRoot.class);

        Assert.assertEquals(pack.getBox2().getBox3().getTotalLen(), UByte.valueOf(bytes.length));
        Assert.assertEquals(boxRoot.getLen(), pack.getLen());
        Assert.assertEquals(pack.getBox2_box3lenStr(), "abcde");
        Assert.assertEquals(pack.getBox1().getLenStr(), "qw");
        Assert.assertEquals(pack.getBox1().getLen2Str(), "asdfgh");

        Assert.assertEquals(pack.getBox2().getBox3Str(), "98765");
        Assert.assertEquals(pack.getBox2().getParentlen(), "zxcv");
        Assert.assertEquals(pack.getBox2().getBox3().getParentlen(), "!@#$%^");
        Assert.assertEquals(pack.getRootLenStr(), "MN");


    }



    /**
     * 测试 dynamicSizeOf 为嵌套对象
     *
     * 测试相对引用和绝对引用
     *
     * @throws InterruptedException
     */
    @Test
    public void testDynamicSizeBox() throws InterruptedException {
        HeadBox1 box1 = new HeadBox1();
        box1.setLen((short) 3);

        HeadBox2 box2 = new HeadBox2();
        box2.setLen(UByte.valueOf(5));
        box2.setFu("abcdefghijk");

        DynamicSizeBox dynamicSizeBox = new DynamicSizeBox();
        dynamicSizeBox.setAge(989);
        dynamicSizeBox.setHead1(box1);
        dynamicSizeBox.setHead2(box2);
        dynamicSizeBox.setData(new byte[]{1,2,3,4,5,6,7});
        dynamicSizeBox.setData2(new byte[]{11,22,33,44,55,66,77});

        byte[] bytes = MagicByte.unpackToByte(dynamicSizeBox);
        Assert.assertEquals(bytes.length, 28);

        MResult<DynamicSizeBox> dynamicSizeBoxMResult = MagicByte.<DynamicSizeBox>packExt(bytes, DynamicSizeBox.class);
        Assert.assertEquals(dynamicSizeBoxMResult.getBytes(), Integer.valueOf(bytes.length));
        DynamicSizeBox pack = dynamicSizeBoxMResult.getData();

        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(DynamicSizeBox.class);


        Assert.assertEquals(dynamicSizeBox.getAge(), pack.getAge());
        Assert.assertEquals(dynamicSizeBox.getHead1().getLen(), pack.getData().length);
        Assert.assertEquals(dynamicSizeBox.getHead2().getLen().intValue(), pack.getData2().length);
        Assert.assertEquals(dynamicSizeBox.getHead1().getLen(), pack.getHead2().getFu().length());
        Assert.assertArrayEquals(pack.getData(), new byte[] {1,2,3});
        Assert.assertArrayEquals(pack.getData2(), new byte[] {11,22,33,44,55});
        Assert.assertEquals(pack.getHead2().getFu(), "abc");


    }


    /**
     * 测试 dynamicSizeOf 为嵌套对象
     *
     * 这里会找不到指定的属性，并且抛出异常
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
     * 这里引用的长度字段再另一个封装对象里
     * classA
     *
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
