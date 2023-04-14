package com.github.misterchangray.core.common;


import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.warpper.*;
import com.github.misterchangray.core.common.entity.custom.AllDataTypes;
import com.github.misterchangray.core.common.entity.custom.*;
import com.github.misterchangray.core.common.simple.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * test simple data type
 * such as byte boolean char int short long float double
 *
 */
public class SimpleTest {

    @Test
    public void testUnsigend() {
        UByte uByte = UByte.valueOf((byte) -23);
        Assert.assertEquals(uByte.get(), 233);
        uByte = UByte.valueOf((byte) 88);
        Assert.assertEquals(uByte.get(), 88);
        uByte = UByte.valueOf((byte) 12);
        Assert.assertEquals(uByte.get(), 12);

        UShort uShort = UShort.valueOf((short) -32767);
        Assert.assertEquals(uShort.get(), 32769);
        uShort = UShort.valueOf((short) 32717);
        Assert.assertEquals(uShort.get(), 32717);
        uShort = UShort.valueOf((short) 106);
        Assert.assertEquals(uShort.get(), 106);

        UInt uInt = UInt.valueOf(0x79000000);
        Assert.assertEquals(uInt.get(), 2030043136);
        uInt = UInt.valueOf(0x7fffffff);
        Assert.assertEquals(uInt.get(), 0x7fffffff);
        uInt = UInt.valueOf(Short.MAX_VALUE - 5);
        Assert.assertEquals(uInt.get(), Short.MAX_VALUE - 5);

        ULong uLong = ULong.valueOf(-333);
        Assert.assertEquals(uLong.get(), new BigInteger("18446744073709551283"));
        uLong = ULong.valueOf(0x4fffffff);
        Assert.assertEquals(uLong.get(), BigInteger.valueOf(0x4fffffff));
        uLong = ULong.valueOf(0);
        Assert.assertEquals(uLong.get(), BigInteger.valueOf(0));
        uLong = ULong.valueOf(23);
        Assert.assertEquals(uLong.get(), BigInteger.valueOf(23));
    }


    @Test
    public void testBigInteger() {
        UNumberObj UNumberObj = new UNumberObj();
        UNumberObj.setA(UNumber.valueOf(335));
        UNumberObj.setB(UNumber.valueOf(336));
        byte[] tmp = MagicByte.unpackToByte(UNumberObj);
        UNumberObj pack = MagicByte.pack(tmp, UNumberObj.class);

        Assert.assertEquals(UNumberObj.getA().get(), pack.getA().get());
        Assert.assertEquals(UNumberObj.getB().get(), pack.getB().get());
    }


    @Test
    public void testDateObjNull() {
        DateObject dateObject = new DateObject();
        dateObject.setA(new Date());
        dateObject.setB(Instant.now());
        byte[] tmp = MagicByte.unpackToByte(dateObject);
        DateObject pack = MagicByte.pack(tmp, DateObject.class);
        Assert.assertEquals(dateObject.getA().getTime(), pack.getA().getTime());
        Assert.assertEquals(dateObject.getB().toEpochMilli(), pack.getB().toEpochMilli());
    }

    @Test
    public void testDateObj() {
        DateObject dateObject = new DateObject();
        dateObject.setA(new Date());
        dateObject.setB(Instant.now());
        dateObject.setC(LocalTime.of(12, 23));
        dateObject.setD(LocalDate.of(2022, 12, 2));
        dateObject.setE(LocalDateTime.of(2022,12,3, 13, 15));

        byte[] tmp = MagicByte.unpackToByte(dateObject);
        DateObject pack = MagicByte.pack(tmp, DateObject.class);


        Assert.assertEquals(dateObject.getA().getTime(), pack.getA().getTime());
        Assert.assertEquals(dateObject.getB().toEpochMilli(), pack.getB().toEpochMilli());

        Assert.assertEquals(dateObject.getC().toSecondOfDay(), pack.getC().toSecondOfDay());

        Assert.assertEquals(dateObject.getD().getYear(), pack.getD().getYear());
        Assert.assertEquals(dateObject.getD().getMonth(), pack.getD().getMonth());
        Assert.assertEquals(dateObject.getD().getDayOfMonth(), pack.getD().getDayOfMonth());

        Assert.assertEquals(dateObject.getE().getYear(), pack.getE().getYear());
        Assert.assertEquals(dateObject.getE().getMonth(), pack.getE().getMonth());
        Assert.assertEquals(dateObject.getE().getDayOfMonth(), pack.getE().getDayOfMonth());
        Assert.assertEquals(dateObject.getE().getHour(), pack.getE().getHour());
        Assert.assertEquals(dateObject.getE().getMinute(), pack.getE().getMinute());
        Assert.assertEquals(dateObject.getE().getSecond(), pack.getE().getSecond());
    }


    @Test
    public void testDynamicString() {
        DynamicString defaultValue = new DynamicString();
        defaultValue.setLen(30);
        defaultValue.setEmail("misterchangray@hotmail.com");

        byte[] tmp = MagicByte.unpackToByte(defaultValue);
        DynamicString pack = MagicByte.pack(tmp, DynamicString.class);
        Assert.assertEquals(pack.getEmail(), defaultValue.getEmail());


        defaultValue.setLen(0);
        tmp = MagicByte.unpackToByte(defaultValue);
        pack = MagicByte.pack(tmp, DynamicString.class);
        Assert.assertEquals(pack.getEmail(), "");


        defaultValue.setLen(10);
        tmp = MagicByte.unpackToByte(defaultValue);
        pack = MagicByte.pack(tmp, DynamicString.class);
        Assert.assertEquals(pack.getEmail(), defaultValue.getEmail().substring(0, 10));


    }


    @Test
    public void testStringObj() {
        StringWithUTF8 defaultValue = new StringWithUTF8();
        defaultValue.setEmail("misterchangray@hotmail.com");
        defaultValue.setName("小王吧");

        byte[] tmp = MagicByte.unpackToByte(defaultValue);
        StringWithUTF8 pack = MagicByte.pack(tmp, StringWithUTF8.class);
        Assert.assertEquals(pack.getEmail(), defaultValue.getEmail());
        Assert.assertEquals(pack.getName(), defaultValue.getName());

    }

    @Test
    public void testCustomDefaultVal() {
        CustomDefaultValue defaultValue = new CustomDefaultValue();
        byte[] tmp = MagicByte.unpackToByte(defaultValue);
        DefaultValue pack = MagicByte.pack(tmp, DefaultValue.class);
        Assert.assertEquals(pack.getB2().intValue(), 3);
        Assert.assertTrue(pack.getBo2());
        Assert.assertEquals(pack.getS2().shortValue(), 8);
        Assert.assertEquals(pack.getI2().intValue(), 10);
        Assert.assertEquals(pack.getL2().longValue(), 11);
        Assert.assertEquals(pack.getF2(), 13.0, 2);
        Assert.assertEquals(pack.getD2(), 16.0, 2);

    }

    @Test
    public void testDefaultVal() {
        DefaultValue defaultValue = new DefaultValue();
        byte[] tmp = MagicByte.unpackToByte(defaultValue);
        DefaultValue pack = MagicByte.pack(tmp, DefaultValue.class);
        Assert.assertEquals(pack.getB2().intValue(), 0);
        Assert.assertFalse(pack.getBo2());
        Assert.assertEquals(pack.getS2().shortValue(), 0);
        Assert.assertEquals(pack.getI2().shortValue(), 0);
        Assert.assertEquals(pack.getL2().shortValue(), 0);
        Assert.assertEquals(pack.getD2(), 0.0, 2);
        Assert.assertEquals(pack.getF2(), 0.0, 2);

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

    /**
     * test collection.
     *
     */
    @Test
    public void testCollection() {
        ColletionObj colletionObj = new ColletionObj();
        colletionObj.setA(new byte[]{1,2,3,4,5});
        colletionObj.setB(new Integer[]{11, 12, 13, 14, 15});;
        List<Long> es = new ArrayList<>();
        es.add(22L);
        es.add(23L);
        es.add(24L);
        es.add(25L);
        es.add(26L);
        colletionObj.setC(es);;

        byte[] tmp = MagicByte.unpackToByte(colletionObj);
        ColletionObj pack = MagicByte.pack(tmp, ColletionObj.class);
        Assert.assertArrayEquals(pack.getA(), colletionObj.getA());
        Assert.assertArrayEquals(pack.getB(), colletionObj.getB());

        for (int i = 0; i < es.size(); i++) {
            Assert.assertEquals(es.get(i), pack.getC().get(i));
        }

    }

    /**
     * test double.
     *
     */
    @Test
    public void testString() {
        StringObj stringObj = new StringObj();
        stringObj.setA("hello");

        byte[] bytes = MagicByte.unpackToByte(stringObj);

        StringObj pack = MagicByte.pack(bytes, StringObj.class);
        Assert.assertEquals(stringObj.getA(), pack.getA());

        // test overflow
        stringObj.setA("helloworld");
        bytes = MagicByte.unpackToByte(stringObj);

        pack = MagicByte.pack(bytes, StringObj.class);
        Assert.assertEquals("hello", pack.getA());

        // test empty
        stringObj.setA("");
        bytes = MagicByte.unpackToByte(stringObj);

        pack = MagicByte.pack(bytes, StringObj.class);
        Assert.assertEquals("",pack.getA());


        // test null
        stringObj.setA(null);
        bytes = MagicByte.unpackToByte(stringObj);

        pack = MagicByte.pack(bytes, StringObj.class);
        Assert.assertEquals("", pack.getA());


    }


    /**
     * test double.
     *
     */
    @Test
    public void testChar() {
        CharObj byteObj = new CharObj();
        byteObj.setA('W');
        byteObj.setB( 'C');

        byte[] bytes = MagicByte.unpackToByte(byteObj);

        CharObj pack = MagicByte.pack(bytes, CharObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals(byteObj.getB(), pack.getB(), 2);

        // test null data
        byteObj.setB(null);
        bytes = MagicByte.unpackToByte(byteObj);

        pack = MagicByte.pack(bytes, CharObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals( (byte)0, pack.getB().charValue());

    }

    /**
     * test double.
     *
     */
    @Test
    public void testDouble() {
        DoubleObj byteObj = new DoubleObj();
        byteObj.setA(7.3D);
        byteObj.setB( 1.4D);

        byte[] bytes = MagicByte.unpackToByte(byteObj);

        DoubleObj pack = MagicByte.pack(bytes, DoubleObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals(byteObj.getB(), pack.getB(), 2);


        // test null data
        byteObj.setB(null);
        bytes = MagicByte.unpackToByte(byteObj);

        pack = MagicByte.pack(bytes, DoubleObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals( (double) 0.0, pack.getB().doubleValue(), 2);

    }




    /**
     * test float.
     *
     */
    @Test
    public void testFloat() {
        FloatObj byteObj = new FloatObj();
        byteObj.setA(3.3F);
        byteObj.setB( 4.4F);

        byte[] bytes = MagicByte.unpackToByte(byteObj);

        FloatObj pack = MagicByte.pack(bytes, FloatObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals(byteObj.getB(), pack.getB(), 2);


        // test null data
        byteObj.setB(null);
        bytes = MagicByte.unpackToByte(byteObj);

        pack = MagicByte.pack(bytes, FloatObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals( (float) 0.0, pack.getB(), 2);
    }



    /**
     * test long.
     *
     */
    @Test
    public void testLong() {
        LongObj byteObj = new LongObj();
        byteObj.setA(300L);
        byteObj.setB( 4333L);

        byte[] bytes = MagicByte.unpackToByte(byteObj);

        LongObj pack = MagicByte.pack(bytes, LongObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals(byteObj.getB(), pack.getB());

        // test null data
        byteObj.setB(null);
        bytes = MagicByte.unpackToByte(byteObj);

        pack = MagicByte.pack(bytes, LongObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals( 0L, pack.getB().longValue());

    }


    /**
     * test short.
     *
     */
    @Test
    public void testShort() {
        ShortObj byteObj = new ShortObj();
        byteObj.setA((short) 3);
        byteObj.setB((short) 4);

        byte[] bytes = MagicByte.unpackToByte(byteObj);

        ShortObj pack = MagicByte.pack(bytes, ShortObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals(byteObj.getB(), pack.getB());

        // test null data
        byteObj.setB(null);
        bytes = MagicByte.unpackToByte(byteObj);

        pack = MagicByte.pack(bytes, ShortObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals( 0, pack.getB().shortValue());
    }

    /**
     * test int.
     *
     */
    @Test
    public void testInt() {
        IntObj byteObj = new IntObj();
        byteObj.setA(2);
        byteObj.setB(3);

        byte[] bytes = MagicByte.unpackToByte(byteObj);

        IntObj pack = MagicByte.pack(bytes, IntObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals(byteObj.getB(), pack.getB());


        // test null data
        byteObj.setB(null);
        bytes = MagicByte.unpackToByte(byteObj);

        pack = MagicByte.pack(bytes, IntObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals( 0, pack.getB().intValue());
    }

    /**
     * test boolean.
     *
     */
    @Test
    public void testBoolean() {
        BooleanObj byteObj = new BooleanObj();
        byteObj.setA(false);
        byteObj.setB(true);

        byte[] bytes = MagicByte.unpackToByte(byteObj);

        BooleanObj pack = MagicByte.pack(bytes, BooleanObj.class);
        Assert.assertEquals(byteObj.isA(), pack.isA());
        Assert.assertEquals(byteObj.getB(), pack.getB());



        // test null data
        byteObj.setB(null);
        bytes = MagicByte.unpackToByte(byteObj);

        pack = MagicByte.pack(bytes, BooleanObj.class);
        Assert.assertEquals(byteObj.isA(), pack.isA());
        Assert.assertEquals( false, pack.getB());
    }


    /**
     * test byte.
     *
     */
    @Test
    public void testByte() {
        ByteObj byteObj = new ByteObj();
        byteObj.setA((byte) 2);
        byteObj.setB((byte) 55);

        byte[] bytes = MagicByte.unpackToByte(byteObj);
        Assert.assertArrayEquals(bytes, new byte[]{2, 55});

        ByteObj pack = MagicByte.pack(bytes, ByteObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals(byteObj.getB(), pack.getB());


        // test null data
        byteObj.setB(null);
        bytes = MagicByte.unpackToByte(byteObj);

        pack = MagicByte.pack(bytes, ByteObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals( 0, pack.getB().byteValue());

    }


}
