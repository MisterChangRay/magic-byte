package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.exception.InvalidParameterException;

import java.util.*;

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
        ClassMetaInfo classMetaInfo = ClassParser.getInstance().parse(clazz);
        afterLink(classMetaInfo);
        return classMetaInfo;
    }

    private static void afterLink(ClassMetaInfo classMetaInfo) {
        List<FieldMetaInfo>
                dynamicFields =  new ArrayList<>(),  // all dynamicSizeOf > 0 fields
                autoTrimFields =  new ArrayList<>(), // all autoTrim = true fields
                calcLengthFields =  new ArrayList<>(), // all calcLength = true fields
                calcCheckCodeFields =  new ArrayList<>() //  all checkCode = true fields
                        ;
        for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFlatFields()) {
            if(fieldMetaInfo.isDynamic()) {
                dynamicFields.add(fieldMetaInfo);
            }
            if(fieldMetaInfo.isAutoTrim()) {
                autoTrimFields.add(fieldMetaInfo);
            }
            if(fieldMetaInfo.isCalcCheckCode()) {
                calcCheckCodeFields.add(fieldMetaInfo);
            }
            if(fieldMetaInfo.isCalcLength()) {
                calcCheckCodeFields.add(fieldMetaInfo);
            }
        }

        if(autoTrimFields.size() > 1) {
            throw new InvalidParameterException("autoTrim only use once in the class; at: " + classMetaInfo.getFullName());
        }

        if(calcCheckCodeFields.size() > 1) {
            throw new InvalidParameterException("calcCheckCode only use once in the class; at: " + classMetaInfo.getFullName());
        }

        if(calcLengthFields.size() > 1) {
            throw new InvalidParameterException("calcLength only use once in the class; at: " + classMetaInfo.getFullName());
        }

        if(dynamicFields.size() > 0 && autoTrimFields.size() > 0) {
            throw new InvalidParameterException("autoTrim & dynamicSizeOf only use one in the class; at: " + classMetaInfo.getFullName());
        }
    }


    public static ClassMetaInfo getClassFieldMetaInfo(Class<?> clazz) {
        ClassMetaInfo classMetaInfo = parseClass(clazz);
        afterLink(classMetaInfo);
        return classMetaInfo;
    }
}
