package com.github.misterchangray.core.customconverter;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.complex.NestingObject;
import com.github.misterchangray.core.customconverter.entity.*;
import com.github.misterchangray.core.exception.InvalidLengthException;
import com.github.misterchangray.core.exception.MagicParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * 自定义序列化测试
 *
 * 1. 整个对象进行测试
 * 2. 单个对象单个属性测试
 * 3. 单个对象多个属性 attachData 测试
 * 4. fixsize 测试
 * 5. list 自定义序列化测试
 */
public class CustomConverterTest {
   static SimpleDateFormat timestampFormatter =  new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 测试fixsize 在成员上使用
     *
     * @throws ParseException
     */
    @Test
    public void testStaff13() throws ParseException {
        Staff13 staff13 = new Staff13();
        staff13.setId(13);
        staff13.setName("ray13");

        byte[] bytes = MagicByte.unpackToByte(staff13);
        Staff13 pack = MagicByte.pack(bytes, Staff13.class);

        Assert.assertEquals(pack.getId() , 13);
        Assert.assertEquals(pack.getName() , "ray13");
        Book2 b2 = (Book2) pack.getBook();
        Assert.assertEquals(b2.getId() , 26);

    }

    /**
     * 测试类成员属性使用Object,并使用自定义转换器返回任意对象
     *
     * @throws ParseException
     */
    @Test
    public void testStaff11() throws ParseException {
        Staff11 staff11 = new Staff11();
        staff11.setId(11);
        staff11.setName("ray11");

        byte[] bytes = MagicByte.unpackToByte(staff11);
        Staff11 pack = MagicByte.pack(bytes, Staff11.class);
        Assert.assertEquals(pack.getId() , 11);
        Assert.assertEquals(pack.getName() , "ray11");
        Book2 b2 = (Book2) pack.getBook();
        Assert.assertEquals(b2.getId() , 26);

    }


    /**
     * 测试对象属性中使用自定义转换器
     * 并且该对象未做任何声明
     *
     * @throws ParseException
     */
    @Test
    public void testStaff10() throws ParseException {
        Staff10 staff10 = new Staff10();
        staff10.setId(10);
        staff10.setName("r10");
        byte[] bytes = MagicByte.unpackToByte(staff10);
        Staff10 pack = MagicByte.pack(bytes, Staff10.class);

        Assert.assertEquals(pack.getId() , 10);
        Assert.assertEquals(pack.getName() , "r10");
        Book2 b2 = (Book2) pack.getBook();
        Assert.assertEquals(b2.getId() , 25);



    }


    /**
     * 在类成员中使用 @MagicConverter, 但是用 @MagicField;
     * 此时会抛出异常
     * @throws ParseException
     */
    @Test
    public void testStaff9() throws ParseException {
        Staff9 staff9 = new Staff9();

        Assert.assertThrows(MagicParseException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                byte[] bytes = MagicByte.unpackToByte(staff9);

            }
        });


    }

    /**
     * 测试直接对类使用自定义转换器
     * @throws ParseException
     */
    @Test
    public void testStaff8() throws ParseException {
        Staff8 staff8 = new Staff8();
        staff8.setId(13);
        staff8.setLength(15);

        byte[] bytes = MagicByte.unpackToByte(staff8);
        Assert.assertEquals(bytes.length , 2);
        Staff8 pack = MagicByte.pack(bytes, Staff8.class);

        Assert.assertEquals(staff8.getId(), pack.getId());
        Assert.assertEquals(staff8.getLength(), pack.getLength());
    }


    /**
     * 测试整个类自定义序列化
     * 并且使用了 fixsize 属性
     *
     * 整个类固定 8 个字节, 配置时固定为10个字节
     * @throws ParseException
     */
    @Test
    public void testStaff7() throws ParseException {
        Staff7 staff7 = new Staff7();
        staff7.setId(33);
        staff7.setLength(22);

        byte[] bytes = MagicByte.unpackToByte(staff7);
        Assert.assertEquals(bytes.length , 10);
        Staff7 pack = MagicByte.pack(bytes, Staff7.class);

        Assert.assertEquals(staff7.getId(), pack.getId());
        Assert.assertEquals(staff7.getLength(), pack.getLength());
    }


    /**
     * 测试当自定义序列化时,
     * 是否能正常填充length 数据
     * @throws ParseException
     */
    @Test
    public void testStaff6() throws ParseException {
        String date = "19920903132216";
        Staff6 staff6 = new Staff6();
        staff6.setId(13);
        staff6.setName("ray");
        Date parse = timestampFormatter.parse(date);
        staff6.setBirthday(parse);
        byte[] bytes = MagicByte.unpackToByte(staff6);
        String s = new String(Arrays.copyOfRange(bytes, 8, 8 + 14));
        Assert.assertEquals(s, date);
        Staff6 pack = MagicByte.pack(bytes, Staff6.class);


        Assert.assertEquals(staff6.getId(), pack.getId());
        Assert.assertEquals(bytes.length, pack.getLength());
        Assert.assertEquals(staff6.getName(), pack.getName());
        Assert.assertEquals(staff6.getBirthday().getTime() / 1000, pack.getBirthday().getTime() / 1000);
    }

    /**
     * 序列化时： data数据中包含所有已序列化的数据(包括 calcLength 也已经调用并序列化)
     * 反序列化时: data数据为传入数据的副本
     * @param data
     * @return
     */
    public static byte[] checker(byte[] data) {
        byte[] res = new byte[1];
        for (int i = 0; i < data.length - 2; i++) {
            res[0] = (byte) (res[0] ^ data[i]);
        }
        return res;
    }

    /**
     * 测试自定义序列化对自动填充字段长度/自动计算校验和
     */
    @Test
    public void testStaff5() {
        Staff5 staff5 = new Staff5();
        staff5.setId(33);
        staff5.setName("Jack");
        ArrayList arrayList = new ArrayList();

        Book3 book = new Book3();
        book.setCode(24);
        book.setId(23);
        arrayList.add(book);
        Book3 book2 = new Book3();
        book2.setCode(26);
        book2.setId(25);
        arrayList.add(book2);
        staff5.setBook(arrayList);

        ArrayList arrayList2 = new ArrayList();

        Book3 book3 = new Book3();
        book3.setCode(28);
        book3.setId(27);
        arrayList2.add(book3);
        staff5.setBook2(arrayList2);

        byte[] bytes = MagicByte.unpackToByte(staff5, CustomConverterTest::checker);
        Staff5 pack = MagicByte.pack(bytes, Staff5.class, CustomConverterTest::checker);

        byte[] checker = CustomConverterTest.checker(bytes);
        Assert.assertEquals(bytes[bytes.length - 1], checker[0]);

        Assert.assertEquals(staff5.getId(), pack.getId());
        Assert.assertEquals(staff5.getName(), pack.getName());
        Assert.assertEquals(staff5.getBook().size(), pack.getBook().size());
        Assert.assertEquals(staff5.getBook2().size(), 1);
        Assert.assertEquals(pack.getBook2().size(), 4);

        Assert.assertEquals(pack.getBook2().get(0).getId(), staff5.getBook2().get(0).getId());
    }


    /**
     * 测试自定义序列化
     * 测试使用自定义序列化后 自动填充长度 属性能否正常工作
     *
     */
    @Test
    public void testStaff4() {
        Staff4 staff4 = new Staff4();
        staff4.setId(33);
        staff4.setName("Jack");
        ArrayList arrayList = new ArrayList();

        Book3 book = new Book3();
        book.setCode(24);
        book.setId(23);
        arrayList.add(book);
        Book3 book2 = new Book3();
        book2.setCode(25);
        book2.setId(24);
        arrayList.add(book2);
        staff4.setBook(arrayList);

        ArrayList arrayList2 = new ArrayList();

        Book3 book3 = new Book3();
        book3.setCode(26);
        book3.setId(25);
        arrayList2.add(book3);
        staff4.setBook2(arrayList2);

        byte[] bytes = MagicByte.unpackToByte(staff4);
        Assert.assertEquals(bytes[7], 44);
        Staff4 pack = MagicByte.pack(bytes, Staff4.class);

        Assert.assertEquals(staff4.getName(), pack.getName());
        Assert.assertEquals(staff4.getId(), pack.getId());
        Assert.assertEquals(44, pack.getLength());
        Assert.assertEquals(staff4.getBook().size(), pack.getBook().size());
        Assert.assertNotEquals(staff4.getBook2().size(), pack.getBook2().size());

    }


    /**
     * 测试自定义序列化 list, array
     * 是否能够正常工作
     *
     */
    @Test
    public void testStaff3() {
        Staff3 staff3 = new Staff3();
        staff3.setId(33);
        staff3.setName("Jack");
        ArrayList arrayList = new ArrayList();

        Book3 book = new Book3();
        book.setCode(24);
        book.setId(23);
        arrayList.add(book);
        Book3 book2 = new Book3();
        book2.setCode(25);
        book2.setId(24);
        arrayList.add(book2);
        staff3.setBook(arrayList);

        ArrayList arrayList2 = new ArrayList();

        Book3 book3 = new Book3();
        book3.setCode(26);
        book3.setId(25);
        arrayList2.add(book3);
        staff3.setBook2(arrayList2);

        byte[] bytes = MagicByte.unpackToByte(staff3);
        Staff3 pack = MagicByte.pack(bytes, Staff3.class);

        Assert.assertEquals(staff3.getId(), pack.getId());
        Assert.assertEquals(staff3.getName(), pack.getName());
        for (int i = 0; i < staff3.getBook().size(); i++) {
            Assert.assertEquals(staff3.getBook().get(i).getCode(), pack.getBook().get(i).getCode());
            Assert.assertEquals(staff3.getBook().get(i).getId(), pack.getBook().get(i).getId());
        }
        for (int i = 0; i < staff3.getBook2().size(); i++) {
            Assert.assertEquals(staff3.getBook().get(i).getCode(), pack.getBook().get(i).getCode());
            Assert.assertEquals(staff3.getBook().get(i).getId(), pack.getBook().get(i).getId());
        }
    }


    /**
     * 测试自定义序列化 fixsize 属性是否正常工作
     *
     */
    @Test
    public void testStaff2() {
        Staff2 staff2 = new Staff2();
        staff2.setId(33);
        staff2.setName("Jack");
        Book book = new Book();
        book.setCreateDate(new Date());
        book.setId(23);
        staff2.setBook(book);

        Book book2 = new Book();
        book2.setCreateDate(new Date());
        book2.setId(24);
        staff2.setBook2(book2);

        byte[] bytes = MagicByte.unpackToByte(staff2);
        Staff2 pack = MagicByte.pack(bytes, Staff2.class);

        Assert.assertEquals(staff2.getBook().getId(), pack.getBook().getId());
        Assert.assertEquals(staff2.getBook2().getId(), pack.getBook2().getId());
        Assert.assertEquals(staff2.getId(), pack.getId());
        Assert.assertEquals(staff2.getName(), pack.getName());

    }


    /**
     * 测试自定义序列化是否正常工作
     */
    @Test
    public void testStaff1() {
        Staff1 staff1 = new Staff1();
        staff1.setId(33);
        staff1.setName("Jack");
        Book book = new Book();
        book.setCreateDate(new Date());
        book.setId(23);
        staff1.setBook(book);

        Book book2 = new Book();
        book2.setCreateDate(new Date());
        book2.setId(24);
        staff1.setBook2(book2);

        byte[] bytes = MagicByte.unpackToByte(staff1);
        Staff1 pack = MagicByte.pack(bytes, Staff1.class);

        Assert.assertEquals(staff1.getBook().getId(), pack.getBook().getId());
        Assert.assertEquals(staff1.getBook2().getId(), pack.getBook2().getId());
        Assert.assertEquals(staff1.getId(), pack.getId());
        Assert.assertEquals(staff1.getName(), pack.getName());

    }




}
