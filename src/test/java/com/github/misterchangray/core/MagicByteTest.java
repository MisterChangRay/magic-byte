package com.github.misterchangray.core;


import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.entity.*;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.CalcUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;

public class MagicByteTest {

    @Test
    public void testPackNestingMulti() {
        HighSchool highSchool = new HighSchool();


        City city = new City();
        city.setName("成都");
        city.setHighSchoolList(new ArrayList<>());
        city.getHighSchoolList().add(highSchool);


        highSchool.setName("one");
        highSchool.setTeachers(new Teacher[]{new Teacher(), new Teacher(), new Teacher()});
        highSchool.setStudentList(new ArrayList<Student>() {{
            this.add(new Student());
            this.add(new Student());
            this.add(new Student());
            this.add(new Student());
        }});

        for (int i = 0; i < highSchool.getTeachers().length; i++) {
            highSchool.getTeachers()[i].setName("Teacher" + i);
            Phone p = new Phone();
            p.setBrand("XM" + i);
            p.setPhone(3000 + i);
            highSchool.getTeachers()[i].setPhone(p);

            highSchool.getTeachers()[i].setAge(30 + i);
        }
        for (int i = 0; i < highSchool.getStudentList().size(); i++) {
            highSchool.getStudentList().get(i).setName("Student" + i);
            highSchool.getStudentList().get(i).setAge(20 + i);
            highSchool.getStudentList().get(i).setBookIds(new byte[]{1, 2, 3, 5, (byte)i});
            highSchool.getStudentList().get(i).setPhones(new long[] {183800, 183800, 183800 + i});
        }

        byte[] tmp = MagicByte.unpackToByte(city);

        City pack = MagicByte.pack(tmp, City.class);
        Assert.assertEquals(city.getName(), pack.getName());
        Assert.assertEquals(city.getHighSchoolList().size(), pack.getHighSchoolList().size());
        Assert.assertEquals(city.getHighSchoolSize(), pack.getHighSchoolSize());


        for (int i = 0; i < city.getHighSchoolList().size(); i++) {
            HighSchool highSchool1 = city.getHighSchoolList().get(i);
            HighSchool highSchool2 = pack.getHighSchoolList().get(i);
            Assert.assertEquals(highSchool1.getTeachers().length, highSchool2.getTeachers().length);
            Assert.assertEquals(highSchool1.getTeachers()[1].getName(), highSchool2.getTeachers()[1].getName());
            Assert.assertEquals(highSchool1.getTeachers()[1].getAge(), highSchool2.getTeachers()[1].getAge());
            Assert.assertEquals(highSchool1.getTeachers()[1].getPhone().getPhone(), highSchool2.getTeachers()[1].getPhone().getPhone());
            Assert.assertEquals(highSchool1.getTeachers()[1].getSexByte(), highSchool2.getTeachers()[1].getSexByte());

            Assert.assertEquals(highSchool1.getTeachers()[2].getName(), highSchool2.getTeachers()[2].getName());
            Assert.assertEquals(highSchool1.getTeachers()[2].getAge(), highSchool2.getTeachers()[2].getAge());
            Assert.assertEquals(highSchool1.getTeachers()[2].getPhone().getPhone(), highSchool2.getTeachers()[2].getPhone().getPhone());
            Assert.assertEquals(highSchool1.getTeachers()[2].getSexByte(), highSchool2.getTeachers()[2].getSexByte());

            Assert.assertEquals(highSchool1.getStudentList().size(), highSchool2.getStudentList().size());
            Assert.assertEquals(highSchool1.getStudentList().get(1).getAge(), highSchool2.getStudentList().get(1).getAge());
            Assert.assertArrayEquals(highSchool1.getStudentList().get(1).getBookIds(), highSchool2.getStudentList().get(1).getBookIds());
            Assert.assertArrayEquals(highSchool1.getStudentList().get(1).getPhones(), highSchool2.getStudentList().get(1).getPhones());
            Assert.assertEquals(highSchool1.getStudentList().get(1).getName(), highSchool2.getStudentList().get(1).getName());


            Assert.assertEquals(highSchool1.getStudentList().size(), highSchool2.getStudentList().size());
            Assert.assertEquals(highSchool1.getStudentList().get(3).getAge(), highSchool2.getStudentList().get(3).getAge());
            Assert.assertArrayEquals(highSchool1.getStudentList().get(3).getBookIds(), highSchool2.getStudentList().get(3).getBookIds());
            Assert.assertArrayEquals(highSchool1.getStudentList().get(3).getPhones(), highSchool2.getStudentList().get(3).getPhones());
            Assert.assertEquals(highSchool1.getStudentList().get(3).getName(), highSchool2.getStudentList().get(3).getName());
        }

    }

    @Test
    public void testAllData() {
        AllDataTypes allDataTypes = new AllDataTypes();
        allDataTypes.setB1((byte) 1);
        allDataTypes.setB2((byte) 2);

        allDataTypes.setBo1(true);
        allDataTypes.setBo2(false);

        allDataTypes.setC1('a');
        allDataTypes.setC2('A');

        allDataTypes.setS1((short) 3);
        allDataTypes.setS2((short) 4);

        allDataTypes.setI1(11);
        allDataTypes.setI2(12);

        allDataTypes.setL1(123L);
        allDataTypes.setL2(345L);

        allDataTypes.setF1(3.14F);
        allDataTypes.setF2(3.1415F);

        allDataTypes.setD1(3.141592D);
        allDataTypes.setD2(3.1415926D);

        allDataTypes.setSt2("string");

        byte[] tmp = MagicByte.unpackToByte(allDataTypes);
        AllDataTypes pack = MagicByte.pack(tmp, AllDataTypes.class);

        Assert.assertEquals(allDataTypes.getB1(), pack.getB1());
        Assert.assertEquals(allDataTypes.getB2(), pack.getB2());
        Assert.assertEquals(allDataTypes.isBo1(), pack.isBo1());
        Assert.assertEquals(allDataTypes.getBo2(), pack.getBo2());

        Assert.assertEquals(allDataTypes.getC1(), pack.getC1());
        Assert.assertEquals(allDataTypes.getC2(), pack.getC2());

        Assert.assertEquals(allDataTypes.getS1(), pack.getS1());
        Assert.assertEquals(allDataTypes.getS2(), pack.getS2());

        Assert.assertEquals(allDataTypes.getI1(), pack.getI1());
        Assert.assertEquals(allDataTypes.getI2(), pack.getI2());

        Assert.assertEquals(allDataTypes.getL1(), pack.getL1());
        Assert.assertEquals(allDataTypes.getL2(), pack.getL2());

        Assert.assertEquals(allDataTypes.getF1(), pack.getF1(), 0.00000);
        Assert.assertEquals(allDataTypes.getF2(), pack.getF2(), 0.00000);

        Assert.assertEquals(allDataTypes.getD1(), pack.getD1(), 0.00000);
        Assert.assertEquals(allDataTypes.getD2(), pack.getD2(), 0.00000);

        Assert.assertEquals(allDataTypes.getSt2(), pack.getSt2());

    }



    @Test
    public void testDynamicString() {
        DynamicString dynamicString = new DynamicString();
        dynamicString.setDate(System.currentTimeMillis());
        dynamicString.setEmail("misterchangray@gmail.com");
        dynamicString.setName("ray");

        byte[] unpack = MagicByte.unpackToByte(dynamicString);

        DynamicString pack = MagicByte.<DynamicString>pack(unpack, DynamicString.class);
        Assert.assertEquals(dynamicString.getLen(), pack.getLen());
        Assert.assertEquals(dynamicString.getEmail(), pack.getEmail());
        Assert.assertEquals(dynamicString.getName(), pack.getName());
        Assert.assertEquals(dynamicString.getDate(), pack.getDate());


    }

    @Test
    public void testHighSchool() {
        HighSchool highSchool = new HighSchool();
        highSchool.setName("1234a");
        // 这里测试设置大小超过配置数量
        highSchool.setStudentCount(5);
        highSchool.setStudentList(new ArrayList<>());
        for (int i = 0; i < highSchool.getStudentCount(); i++) {
            Student student = new Student();
            student.setAge(21 + i);
            student.setName("张" + i);
            student.setBookIds(new byte[]{1,2,3,4, (byte) i});
            highSchool.getStudentList().add(student);
        }


        highSchool.setTeacherCount(5);
        highSchool.setTeachers(new Teacher[highSchool.getTeacherCount()]);
        for (int i = 0; i < highSchool.getTeacherCount(); i++) {
            Teacher teacher = new Teacher();
            teacher.setAge(41 + i);
            teacher.setName("t" + i);

            Phone p = new Phone();
            p.setBrand("XM");
            p.setPhone(13000 + i);
            teacher.setPhone(p);
            highSchool.getTeachers()[i] = teacher;
        }

        byte[] tmp = MagicByte.unpackToByte(highSchool);



        HighSchool pack = MagicByte.pack(tmp, HighSchool.class);


        Assert.assertNotNull(pack);
        Assert.assertEquals(highSchool.getName(), pack.getName());
        Assert.assertEquals(highSchool.getTeacherCount(), pack.getTeacherCount());
        Assert.assertEquals(highSchool.getTeachers()[1].getAge(), pack.getTeachers()[1].getAge());
        Assert.assertEquals(highSchool.getTeachers()[1].getName(), pack.getTeachers()[1].getName());

        Assert.assertEquals(highSchool.getStudentCount(), pack.getStudentCount());
        Assert.assertEquals(highSchool.getStudentList().get(1).getAge(), pack.getStudentList().get(1).getAge());
        Assert.assertEquals(highSchool.getStudentList().get(1).getAge(), pack.getStudentList().get(1).getAge());

        Assert.assertArrayEquals(highSchool.getStudentList().get(2).getBookIds(), pack.getStudentList().get(2).getBookIds());
        Assert.assertEquals(highSchool.getTeachers()[2].getPhone().getPhone(), pack.getTeachers()[2].getPhone().getPhone());

    }



    @Test
    public void testSimpleObject() {
        Object pack = MagicByte.pack(new byte[]{1, 2, 3}, EmptyObject.class);
        Assert.assertNull(pack);

        byte[] tmp = MagicByte.unpackToByte(new EmptyObject());
    }


    @Test
    public void testUtil() {
        BigInteger bigInteger = new BigInteger("-12");

        System.out.println(CalcUtil.byteToHexString(bigInteger.toByteArray()));;

        System.out.println(CalcUtil.hexStringToByte("ABCD0F"));
    }

    /**
     * 检测部分数据是否 能正常加载
     */
    @Test
    public void testObjectData() throws UnsupportedEncodingException, InstantiationException {
        Student student = new Student();
        student.setAge(39);
        student.setPhones(new long[]{1838, 238, 234});
        student.setName("ray");
        student.setBookIds(new byte[]{1,2,3,4,5});

        byte[] tmp = MagicByte.unpackToByte(student);

        tmp = Arrays.copyOf(tmp, 250);
        Student student1 = MagicByte.pack(tmp, Student.class);


        Assert.assertEquals(student.getName(), student1.getName());
        Assert.assertEquals(student.getAge(), student1.getAge());
        Assert.assertEquals(student.getPhones()[2], student1.getPhones()[2]);
    }


    /**
     * 检测部分数据是否 能正常加载
     */
    @Test
    public void testPartOfAllData() throws UnsupportedEncodingException, InstantiationException {
        Student student = new Student();
        student.setAge(39);
        student.setPhones(new long[]{1838, 238, 234});
        student.setName("ray");
        student.setBookIds(new byte[]{1,2,3,4,5});

        byte[] tmp = MagicByte.unpackToByte(student);

        tmp = Arrays.copyOf(tmp, 25);
        boolean hasException = false;
        try {
            Student student1 = MagicByte.pack(tmp, Student.class);

            Assert.assertEquals(student.getName(), student1.getName());
            Assert.assertEquals(student.getAge(), student1.getAge());
            Assert.assertArrayEquals(student.getPhones(), student1.getPhones());
            Assert.assertArrayEquals(student.getBookIds(), student1.getBookIds());
        } catch (Exception ae) {
            hasException = true;
        }

        Assert.assertTrue("part of all data in strict model should be throw exception", hasException);
    }

    /**
     * 测试未知字段对序列化是否有影响,
     *
     * StringBuffer
     * StringBuilder
     * Map
     * Date
     * List嵌套
     */
    @Test
    public void testUnknownField() throws UnsupportedEncodingException, InstantiationException {
        UnknownField unknownField = new UnknownField();
        unknownField.setaDouble(1.2);
        unknownField.setaFloat((float) 2.3);
        unknownField.setName("FUCK");
        unknownField.setStringBuffer(new StringBuffer("bufer"));
        unknownField.setStringBuilder(new StringBuilder("build"));
        unknownField.setTime(new Date());
        unknownField.setTmo(new HashMap<>());
        unknownField.setCreateTime(System.currentTimeMillis());


        boolean hasException = false;
        try {
            byte[] b = MagicByte.unpackToByte(unknownField);
            UnknownField unknownField1 = MagicByte.pack(b, UnknownField.class);

            Assert.assertNull(unknownField1.getStringBuffer());
            Assert.assertNull(unknownField1.getStringBuilder());
            Assert.assertNull(unknownField1.getTime());
            Assert.assertNull(unknownField1.getTmo());
            Assert.assertEquals(unknownField.getName(), unknownField1.getName());
            Assert.assertEquals("double should be equals", unknownField.getaDouble(), unknownField1.getaDouble(), 0.0);
            Assert.assertEquals("float should be equals", unknownField.getaFloat(), unknownField1.getaFloat(), 0.0);
            Assert.assertEquals(unknownField.getCreateTime(), unknownField1.getCreateTime());
        } catch (MagicByteException ae) {
            ae.printStackTrace();
            hasException= true;
        }

        Assert.assertFalse(hasException);
    }

    /**
     * 测试正常单个对象序列化是否正常
     * @throws UnsupportedEncodingException
     * @throws InstantiationException
     */
    @Test
    public void testPackSingle() throws UnsupportedEncodingException, InstantiationException {
        Student student = new Student();
        student.setAge(39);
        student.setPhones(new long[]{1838, 238, 234});
        student.setName("ray");
        student.setBookIds(new byte[]{1,2,3,4,5});

        byte[] tmp = MagicByte.unpackToByte(student);

        Student student1 = MagicByte.pack(tmp, Student.class);

        Assert.assertEquals(student.getAge(), student1.getAge());
        Assert.assertArrayEquals(student.getPhones(), student1.getPhones());
        Assert.assertEquals(student.getName(), student1.getName());
        Assert.assertArrayEquals(student.getBookIds(), student1.getBookIds());

    }


    /**
     * 测试数组或字符串溢出时是否正常
     * @throws UnsupportedEncodingException
     * @throws InstantiationException
     */
    @Test
    public void testListOverflow() throws UnsupportedEncodingException, InstantiationException {
        long[] phones = new long[]{1838, 238, 234, 34, 45, 56, 67, 78, 89, 90};
        byte[] tmps = new byte[]{1,2,3,4,5,12,43,23};
        Student student = new Student();
        student.setAge(39);
        student.setPhones(phones);
        student.setName("misterchangray");
        student.setBookIds(tmps);

        byte[] tmp = MagicByte.unpackToByte(student);

        Student student1 = MagicByte.pack(tmp, Student.class);

        Assert.assertArrayEquals(Arrays.copyOf(student.getBookIds(),5), student1.getBookIds());
        Assert.assertArrayEquals( Arrays.copyOf(student.getPhones(),3),student1.getPhones());
        Assert.assertEquals(student.getName().substring(0,10), student1.getName());
        Assert.assertEquals(student.getAge(), student1.getAge());
    }

    /**
     * 测试对象嵌套序列化是否正常
     * @throws UnsupportedEncodingException
     * @throws InstantiationException
     */
    @Test
    public void testPackNesting() throws UnsupportedEncodingException, InstantiationException {
        School school = new School();
        school.setName("XiHuaDaXe");

        List<Student> studentList = new ArrayList<>();
        for(int i=0; i<4; i++) {
            Student student = new Student();
            student.setAge(23 + i);
            student.setPhones(new long[]{1838 + i, 238, 234});
            student.setName("ray" + i);
            student.setBookIds(new byte[]{(byte) (1+i), (byte) (2+i),3,4,5});
            studentList.add(student);
        }
        school.setStudentList(studentList);


        Teacher[] teachers = new Teacher[3];
        for(int i=0; i<3; i++) {
            Teacher teacher = new Teacher();
            teacher.setAge(40 + i);
            teacher.setName("teacher" + i);
            teacher.setSexChar('我');

            Phone phone = new Phone();
            phone.setBrand("XIAOM" + i);
            phone.setPhone(1838 + i);
            teacher.setPhone(phone);
            teacher.setSex(true);
            teacher.setSexByte((byte) (4 + i));
            teachers[i] =teacher;
        }
        school.setTeachers(teachers);


        byte[] tmp = MagicByte.unpackToByte(school);

        School school1 = MagicByte.pack(tmp, School.class);

        Assert.assertEquals(school.getName(), school1.getName().trim());
        for(int i=0; i<school.getStudentList().size(); i++) {
            Student o = school.getStudentList().get(i);
            Student o2 = school1.getStudentList().get(i);
            Assert.assertEquals(o.getAge() , o2.getAge());
            Assert.assertEquals(o.getName(), o2.getName().trim());
            Assert.assertArrayEquals(o.getPhones(), o2.getPhones());
            Assert.assertArrayEquals(o.getBookIds(), o.getBookIds());
        }

        for(int i=0; i<school1.getTeachers().length; i++) {
            Teacher o = school.getTeachers()[i];
            Teacher o2 = school1.getTeachers()[i];

            Assert.assertEquals(o.getAge() , o2.getAge());
            Assert.assertEquals(o.getName().trim(), o2.getName().trim());
            Assert.assertEquals(o.getSex(), o2.getSex());
            Assert.assertEquals(o.getSexChar(), o2.getSexChar());
            Assert.assertEquals(o.getSexByte(), o2.getSexByte());

            Assert.assertEquals(o.getPhone().getBrand(), o2.getPhone().getBrand());
            Assert.assertEquals(o.getPhone().getPhone(), o2.getPhone().getPhone());
        }
    }

}
