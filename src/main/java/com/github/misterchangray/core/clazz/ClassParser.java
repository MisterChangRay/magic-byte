package com.github.misterchangray.core.clazz;


import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.util.AnnotationUtil;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Comparator;
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

    private void beforeLinkClazz(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        classMetaInfo.setClazz(clazz);
        classMetaInfo.setFullName(clazz.getName());
        classMetaInfo.setByteOrder(ByteOrder.BIG_ENDIAN);
        classMetaInfo.setFields(new ArrayList<>());

        this.copyConfiguration(classMetaInfo, clazz);
    }


    /**
     * 收尾工作
     * - 统计总字节数
     * - 链接动态字段引用
     *
     * @param classMetaInfo
     * @param clazz
     */
    private void afterLinkClazz(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        if(Objects.nonNull(classMetaInfo.getFields()) && classMetaInfo.getFields().size() == 0) return;

        int total = 0;
        classMetaInfo.getFields().sort((Comparator.comparingInt(FieldMetaInfo::getOrderId)));
        for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            total += fieldMetaInfo.getElementBytes() * fieldMetaInfo.getSize();

            if(fieldMetaInfo.isDynamic() ){
                FieldMetaInfo dynamicRef =
                        fieldMetaInfo.getOwnerClazz().getFieldMetaInfoByOrderId(fieldMetaInfo.getDynamicSizeOf());
                if(Objects.isNull(dynamicRef)) {
                    throw new InvalidParameterException("not found  target field of dynamicSizeOf value; at: " + fieldMetaInfo.getFullName());
                }

                if(dynamicRef.getOrderId() > fieldMetaInfo.getOrderId()) {
                    throw new InvalidParameterException("dynamicSizeOf property value should be less than itself order; at: " + fieldMetaInfo.getFullName());
                }

                fieldMetaInfo.setDynamicRef(dynamicRef);
                dynamicRef.setDynamicRef(fieldMetaInfo);

                if(dynamicRef.getType() != TypeEnum.BYTE &&
                        dynamicRef.getType() != TypeEnum.SHORT &&
                        dynamicRef.getType() != TypeEnum.INT) {
                    throw new InvalidParameterException("dynamic refs the type of filed must be primitive and only be byte, short, int; at: " + fieldMetaInfo.getFullName());
                }
            }
        }
        classMetaInfo.setElementBytes(total);
    }


    private void copyConfiguration(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        MagicClass magicClass = AnnotationUtil.getMagicClassAnnotation(clazz);
        if(Objects.isNull(magicClass)) return;

        classMetaInfo.setByteOrder(magicClass.byteOrder().getBytes());
        classMetaInfo.setStrict(magicClass.strict());
    }

}
