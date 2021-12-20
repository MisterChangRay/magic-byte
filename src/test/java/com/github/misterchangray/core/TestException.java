package com.github.misterchangray.core;


import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.ClassParser;
import com.github.misterchangray.core.errorEntity.TestArrayMatrix;
import com.github.misterchangray.core.errorEntity.TestListMatrix;
import com.github.misterchangray.core.exception.MagicParseException;
import org.junit.Assert;
import org.junit.Test;

public class TestException {


    /**
     * 测试数组必须大于声明.
     *
     */
    @Test
    public void TestListMatrix() {
        ClassParser classParser = new ClassParser();
        ClassMetaInfo parse = classParser.parse(TestListMatrix.class);
//        Assert.assertThrows(MagicParseException.class, () -> {
//            ClassMetaInfo parse = classParser.parse(TestListMatrix.class);
//        });
    }

    @Test
    public void TestArrayMatrix() {
        ClassParser classParser = new ClassParser();
        Assert.assertThrows(MagicParseException.class, () -> {
            ClassMetaInfo parse = classParser.parse(TestArrayMatrix.class);
        });
    }

}
