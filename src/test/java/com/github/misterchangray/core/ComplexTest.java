package com.github.misterchangray.core;


import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.ClassParser;
import com.github.misterchangray.core.entity.UnknownType;
import com.github.misterchangray.core.errorEntity.TestArrayMatrix;
import com.github.misterchangray.core.errorEntity.TestListMatrix;
import com.github.misterchangray.core.errorEntity.TestSameOrder;
import com.github.misterchangray.core.exception.MagicParseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.List;

public class ComplexTest {

    /**
     * 测试数组必须大于声明.
     *
     */
    @Test
    public void testUnknownType() throws InterruptedException {
        UnknownType unknownType = UnknownType.build(2).get(0);
        byte[] bytes = MagicByte.unpackToByte(unknownType);
        Thread.sleep(1000);
        UnknownType pack = MagicByte.pack(bytes, UnknownType.class);

        System.out.println(1);

    }


}
