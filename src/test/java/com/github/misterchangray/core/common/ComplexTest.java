package com.github.misterchangray.core.common;


import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.common.dynamic.DynamicClasses;
import com.github.misterchangray.core.common.dynamic.DynamicStudent;
import com.github.misterchangray.core.common.dynamic.DynamicTeacher;
import com.github.misterchangray.core.common.entity.Classes;
import com.github.misterchangray.core.common.entity.Student;
import com.github.misterchangray.core.common.entity.Teacher;
import com.github.misterchangray.core.common.entity.custom.UnknownType;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ComplexTest {


    /**
     * 部分数据测试, 生成数据后删除一部分
     * @throws InterruptedException
     */
    @Test
    public void testDynamicObjectDataLoss() throws InterruptedException {
        // use non strict model
        DynamicClasses classes1 = DynamicClasses.build(1).get(0);

        ByteBuffer unpack = MagicByte.unpack(classes1);
        byte[] tmp = Arrays.copyOfRange(unpack.array(), 0, 55);
        DynamicClasses pack = MagicByte.pack(tmp, DynamicClasses.class);

        for (int i = 0; i < 1; i++) {
            DynamicTeacher teacher1 = classes1.getTeacher()[i];
            DynamicTeacher teacher2 = pack.getTeacher()[i];
            Assert.assertEquals(teacher1.getId(), teacher2.getId());
            Assert.assertEquals(teacher1.getName(), teacher2.getName());
            Assert.assertArrayEquals(teacher1.getPhones(), teacher2.getPhones());
        }

    }


    /**
     * 动态对象测试
     * @throws InterruptedException
     */
    @Test
    public void testDynamicObject() throws InterruptedException {
        DynamicClasses classes1 = DynamicClasses.build(1).get(0);

        byte[] bytes = MagicByte.unpackToByte(classes1);
        DynamicClasses pack = MagicByte.pack(bytes, DynamicClasses.class);



        for (int i = 0; i < classes1.getTeacher().length; i++) {
            DynamicTeacher teacher1 = classes1.getTeacher()[i];
            DynamicTeacher teacher2 = pack.getTeacher()[i];
            Assert.assertEquals(teacher1.getId(), teacher2.getId());
            Assert.assertEquals(teacher1.getName(), teacher2.getName());
            Assert.assertArrayEquals(teacher1.getPhones(), teacher2.getPhones());

        }

        for (int i = 0; i < classes1.getStudentList().size(); i++) {
            DynamicStudent student1 = classes1.getStudentList().get(i);
            DynamicStudent student2 = pack.getStudentList().get(i);
            Assert.assertEquals(student1.getPhone(), student2.getPhone());
            Assert.assertEquals(student1.getName(), student2.getName());
            Assert.assertArrayEquals(student1.getBookids(), student2.getBookids());
            Assert.assertEquals(student1.getEmailLen(), student2.getEmailLen());
            Assert.assertNotEquals(student1.getEmail(), student2.getEmail());

            Assert.assertEquals(student1.getBookLen(), student2.getBookLen());
            Assert.assertNotEquals(student1.getBookids(), student2.getBookids());

        }
    }


    /**
     * 数组元素超出测试,
     * 实际数组元素大于声明大小
     * @throws InterruptedException
     */
    @Test
    public void testArrayOverflow2() throws InterruptedException {
        Classes classes1 = Classes.build(1).get(0);
        classes1.setTeacher(Arrays.copyOf(classes1.getTeacher(), 1));

        byte[] bytes = MagicByte.unpackToByte(classes1);
        Classes pack = MagicByte.pack(bytes, Classes.class);


        for (int i = 0; i < classes1.getTeacher().length; i++) {
            Teacher teacher1 = classes1.getTeacher()[i];
            Teacher teacher2 = pack.getTeacher()[i];
            Assert.assertEquals(teacher1.getId(), teacher2.getId());
            Assert.assertEquals(teacher1.getName(), teacher2.getName());
            Assert.assertArrayEquals(teacher1.getPhones(), teacher2.getPhones());

        }

        for (int i = 0; i < classes1.getStudentList().size(); i++) {
            Student student1 = classes1.getStudentList().get(i);
            Student student2 = pack.getStudentList().get(i);
            Assert.assertEquals(student1.getPhone(), student2.getPhone());
            Assert.assertEquals(student1.getName(), student2.getName());
            Assert.assertArrayEquals(student1.getBookids(), student2.getBookids());

        }
    }



    /**
     * test for array byte fill.
     * list 实际大小大于声明大小
     * 应该自动裁剪
     *
     */
    @Test
    public void testListOverflow() throws InterruptedException {
        Classes classes1 = Classes.build(1).get(0);
        Classes classes2 = Classes.build(1).get(0);

        classes1.getStudentList().addAll(classes2.getStudentList());

        byte[] bytes = MagicByte.unpackToByte(classes1);
        Classes pack = MagicByte.pack(bytes, Classes.class);


        for (int i = 0; i < classes1.getTeacher().length; i++) {
            Teacher teacher1 = classes1.getTeacher()[i];
            Teacher teacher2 = pack.getTeacher()[i];
            Assert.assertEquals(teacher1.getId(), teacher2.getId());
            Assert.assertEquals(teacher1.getName(), teacher2.getName());
            Assert.assertArrayEquals(teacher1.getPhones(), teacher2.getPhones());

        }
        for (int i = 0; i < pack.getStudentList().size(); i++) {
            Student student1 = classes1.getStudentList().get(i);
            Student student2 = pack.getStudentList().get(i);
            Assert.assertEquals(student1.getPhone(), student2.getPhone());
            Assert.assertEquals(student1.getName(), student2.getName());
            Assert.assertArrayEquals(student1.getBookids(), student2.getBookids());

        }
    }

    /**
     * test for array byte fill.
     * 实际list大小 小于声明大小，应该自动填充
     */
    @Test
    public void testListFill() throws InterruptedException {
        Classes classes1 = Classes.build(1).get(0);

        classes1.getStudentList().remove(0);

        byte[] bytes = MagicByte.unpackToByte(classes1);
        Classes pack = MagicByte.pack(bytes, Classes.class);

        for (int i = 0; i < classes1.getStudentList().size(); i++) {
            Student student1 = classes1.getStudentList().get(i);
            Student student2 = pack.getStudentList().get(i);
            Assert.assertEquals(student1.getPhone(), student2.getPhone());
            Assert.assertEquals(student1.getName(), student2.getName());
            Assert.assertArrayEquals(student1.getBookids(), student2.getBookids());

        }

    }


    /**
     * test for array byte fill.
     * 实际数组元素小于声明大小， 应该自动填充
     */
    @Test
    public void testArrayOverflow() throws InterruptedException {
        Student student = Student.build(1).get(0);
        student.setBookids(new int[]{22, 33, 55, 66});

        byte[] bytes = MagicByte.unpackToByte(student);
        Student pack = MagicByte.pack(bytes, Student.class);


        Assert.assertEquals(student.getPhone(), pack.getPhone());
        Assert.assertArrayEquals(pack.getBookids(), new int[]{22, 33, 55});

    }

    /**
     * test for array byte fill.
     * 数组自动填充测试
     */
    @Test
    public void testArrayFill() throws InterruptedException {
        Student student = Student.build(1).get(0);
        student.setBookids(new int[]{22});

        byte[] bytes = MagicByte.unpackToByte(student);
        Student pack = MagicByte.pack(bytes, Student.class);

        Assert.assertEquals(student.getPhone(), pack.getPhone());

    }


    /**
     * 未知数据类型.
     * 未知数据类型测试
     *
     */
    @Test
    public void testUnknownType() throws InterruptedException {
        UnknownType unknownType = UnknownType.build(2).get(0);
        byte[] bytes = MagicByte.unpackToByte(unknownType);
        UnknownType pack = MagicByte.pack(bytes, UnknownType.class);

        Assert.assertNotNull(pack.getBrithday());
        Assert.assertNull(pack.getAttr());
        Assert.assertNull(pack.getClazz());
        Assert.assertNotNull(pack.getRemark());
        Assert.assertNull(pack.getOpt());

        Assert.assertEquals(unknownType.getId(), pack.getId());
        Assert.assertEquals(unknownType.getName(), pack.getName());
        Assert.assertEquals(unknownType.getPhoneSize(), pack.getPhoneSize());
        Assert.assertArrayEquals(unknownType.getPhones(), pack.getPhones());

        Assert.assertEquals(unknownType.getAge(), pack.getAge());
        Assert.assertEquals(unknownType.getTtl(), pack.getTtl());
        Assert.assertEquals(unknownType.getHaha(), pack.getHaha());


    }


}
