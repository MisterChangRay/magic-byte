package com.github.misterchangray.core.complex;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.util.ConverterUtil;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class NestingTest {



    @Test
    public void testNestingObject() {
        NestingObject nestingObject = new NestingObject();
        nestingObject.setId(2);
        nestingObject.setName("level1");

        NestingObject.Level2 level2 = new NestingObject.Level2();
        level2.setName("level2");
        level2.setId((byte) 3);

        nestingObject.setLevel2(level2);


        byte[] unpack = MagicByte.unpackToByte(nestingObject);
        NestingObject tmp = MagicByte.pack(unpack, NestingObject.class);

        Assert.assertEquals(nestingObject.getId(), tmp.getId());
        Assert.assertEquals(nestingObject.getName(), tmp.getName());
        Assert.assertEquals(nestingObject.getLevel2().getId(), tmp.getLevel2().getId());
        Assert.assertEquals(nestingObject.getLevel2().getName(), tmp.getLevel2().getName());
    }




}
