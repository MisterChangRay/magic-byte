package com.github.misterchangray.core.util;

import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.exception.OutOfMemoryDetecteException;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 09:54
 **/
public class AssertUtil {
    public static void throwIllegalAccessException(ClassMetaInfo classMetaInfo) {
        throw new MagicByteException("class must be declared public; inner class is not supported; at: " + classMetaInfo.getFullName());
    }

    public static void throwIFOOM(long allocBytes, String at) {
        // direct return if allocate size less than 1k
        if(allocBytes < 1024) {
            return;
        }
        if(allocBytes > Runtime.getRuntime().freeMemory()) {
            throw new OutOfMemoryDetecteException("detected OutOfMemory, no have enough free space allocate! at: " + at);
        }

    }
}
