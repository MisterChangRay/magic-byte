package com.github.misterchangray.core.util;

import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.exception.MagicByteException;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 09:54
 **/
public class AssertUtil {
    public static void throwIllegalAccessException(ClassMetaInfo classMetaInfo) {
        throw new MagicByteException("class must be declared public; inner class is not supported; at: " + classMetaInfo.getFullName());
    }
}
