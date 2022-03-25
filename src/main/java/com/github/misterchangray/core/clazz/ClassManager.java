package com.github.misterchangray.core.clazz;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description: class manager
 * @author: Ray.chang
 * @create: 2021-12-17 15:11
 **/
public class ClassManager {
    private static Map<Class<?>, ClassMetaInfo> cache = new HashMap<>();

    public static ClassMetaInfo getClassMetaInfo(Class<?> clazz) {
        ClassMetaInfo classMetaInfo = cache.get(clazz);
        if (null != classMetaInfo) {
            return classMetaInfo;
        }

        classMetaInfo = parseClass(clazz);
        cache.put(clazz, classMetaInfo);
        return classMetaInfo;
    }

    private static ClassMetaInfo parseClass(Class<?> clazz) {
        return ClassParser.getInstance().parse(clazz);
    }


    public static ClassMetaInfo getClassFieldMetaInfo(Class<?> clazz) {
        return parseClass(clazz);
    }
}
