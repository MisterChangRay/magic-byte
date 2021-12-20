package com.github.misterchangray.core;


import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.ClassParser;
import com.github.misterchangray.core.errorEntity.TestArrayMatrix;
import com.github.misterchangray.core.errorEntity.TestListMatrix;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.simple.*;
import org.junit.Assert;
import org.junit.Test;


/**
 * test simple data type
 * such as byte boolean char int short long float double
 *
 */
public class SimpleTest {

    /**
     * test double.
     *
     */
    @Test
    public void testString() {
        StringObj stringObj = new StringObj();
        stringObj.setA("hello");

        byte[] bytes = UnPacker.getInstance().unpackObject(stringObj);

        StringObj pack = MagicByte.pack(bytes, StringObj.class);
        Assert.assertEquals(stringObj.getA(), pack.getA());

        // test overflow
        stringObj.setA("helloworld");
        bytes = UnPacker.getInstance().unpackObject(stringObj);

        pack = MagicByte.pack(bytes, StringObj.class);
        Assert.assertEquals("hello", pack.getA());

        // test empty
        stringObj.setA("");
        bytes = UnPacker.getInstance().unpackObject(stringObj);

        pack = MagicByte.pack(bytes, StringObj.class);
        Assert.assertEquals("",pack.getA());


        // test null
        stringObj.setA(null);
        bytes = UnPacker.getInstance().unpackObject(stringObj);

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

        byte[] bytes = UnPacker.getInstance().unpackObject(byteObj);

        CharObj pack = MagicByte.pack(bytes, CharObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals(byteObj.getB(), pack.getB(), 2);

        // test null data
        byteObj.setB(null);
        bytes = UnPacker.getInstance().unpackObject(byteObj);

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

        byte[] bytes = UnPacker.getInstance().unpackObject(byteObj);

        DoubleObj pack = MagicByte.pack(bytes, DoubleObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals(byteObj.getB(), pack.getB(), 2);


        // test null data
        byteObj.setB(null);
        bytes = UnPacker.getInstance().unpackObject(byteObj);

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

        byte[] bytes = UnPacker.getInstance().unpackObject(byteObj);

        FloatObj pack = MagicByte.pack(bytes, FloatObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA(), 2);
        Assert.assertEquals(byteObj.getB(), pack.getB(), 2);


        // test null data
        byteObj.setB(null);
        bytes = UnPacker.getInstance().unpackObject(byteObj);

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

        byte[] bytes = UnPacker.getInstance().unpackObject(byteObj);

        LongObj pack = MagicByte.pack(bytes, LongObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals(byteObj.getB(), pack.getB());

        // test null data
        byteObj.setB(null);
        bytes = UnPacker.getInstance().unpackObject(byteObj);

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

        byte[] bytes = UnPacker.getInstance().unpackObject(byteObj);

        ShortObj pack = MagicByte.pack(bytes, ShortObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals(byteObj.getB(), pack.getB());

        // test null data
        byteObj.setB(null);
        bytes = UnPacker.getInstance().unpackObject(byteObj);

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

        byte[] bytes = UnPacker.getInstance().unpackObject(byteObj);

        IntObj pack = MagicByte.pack(bytes, IntObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals(byteObj.getB(), pack.getB());


        // test null data
        byteObj.setB(null);
        bytes = UnPacker.getInstance().unpackObject(byteObj);

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

        byte[] bytes = UnPacker.getInstance().unpackObject(byteObj);

        BooleanObj pack = MagicByte.pack(bytes, BooleanObj.class);
        Assert.assertEquals(byteObj.isA(), pack.isA());
        Assert.assertEquals(byteObj.getB(), pack.getB());



        // test null data
        byteObj.setB(null);
        bytes = UnPacker.getInstance().unpackObject(byteObj);

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

        byte[] bytes = UnPacker.getInstance().unpackObject(byteObj);
        Assert.assertArrayEquals(bytes, new byte[]{2, 55});

        ByteObj pack = MagicByte.pack(bytes, ByteObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals(byteObj.getB(), pack.getB());


        // test null data
        byteObj.setB(null);
        bytes = UnPacker.getInstance().unpackObject(byteObj);

        pack = MagicByte.pack(bytes, ByteObj.class);
        Assert.assertEquals(byteObj.getA(), pack.getA());
        Assert.assertEquals( 0, pack.getB().byteValue());

    }


}
