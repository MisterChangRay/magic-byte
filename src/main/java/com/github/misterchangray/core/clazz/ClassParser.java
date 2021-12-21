package com.github.misterchangray.core.clazz;


import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.util.AnnotationUtil;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 15:18
 **/
public class ClassParser {
    private static ClassParser classParser;

    public static ClassParser getInstance() {
        if(null == classParser) {
            classParser = new ClassParser();
        }
        return classParser;
    }


    public ClassMetaInfo parse(Class<?> clazz) {
        ClassMetaInfo classMetaInfo = new ClassMetaInfo();
        TypeEnum type = TypeManager.getType(clazz);
        if(type.getBytes() > 0) {
            classMetaInfo.setElementBytes(type.getBytes());
            return classMetaInfo;
        }

        this.beforeLinkClazz(classMetaInfo, clazz);

        for (Field field : clazz.getDeclaredFields()) {
            FieldMetaInfo fieldMetaInfo = FieldParser.getInstance().parse(field, classMetaInfo);
            if(null == fieldMetaInfo) {
                continue;
            }

            classMetaInfo.getFields().add(fieldMetaInfo);
        }

        this.afterLinkClazz(classMetaInfo, clazz);
        return classMetaInfo;

    }

    /**
     * 收尾工作
     * - 统计总字节数
     * - 判断是否为动态大小
     * @param classMetaInfo
     * @param clazz
     */
    private void afterLinkClazz(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        int total = 0;
        for (FieldMetaInfo field : classMetaInfo.getFields()) {
            total += field.getElementBytes() * field.getSize();
        }
        classMetaInfo.setElementBytes(total);
    }

    private void beforeLinkClazz(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        classMetaInfo.setClazz(clazz);
        classMetaInfo.setFullName(clazz.getName());
        classMetaInfo.setByteOrder(ByteOrder.BIG_ENDIAN);
        classMetaInfo.setFields(new ArrayList<>());

        this.copyConfiguration(classMetaInfo, clazz);
    }

    private void copyConfiguration(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        MagicClass magicClass = AnnotationUtil.getMagicClassAnnotation(clazz);
        if(Objects.isNull(magicClass)) return;

        classMetaInfo.setAutoTrim(magicClass.autoTrim());
        classMetaInfo.setByteOrder(magicClass.byteOrder().getBytes());
        classMetaInfo.setFillByte(magicClass.fillByte());
    }

}
