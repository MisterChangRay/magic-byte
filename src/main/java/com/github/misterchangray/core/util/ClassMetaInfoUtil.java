package com.github.misterchangray.core.util;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.enums.ByteOrder;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.metainfo.ClassMetaInfo;
import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import javax.swing.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class ClassMetaInfoUtil {
    private static Map<Class<?>, ClassMetaInfo> cache = new HashMap<Class<?>, ClassMetaInfo>(1000);


    /**
     * 将原始类进行包装;并将包装结果缓存,提高性能
     *
     * @param clazz
     * @return
     */
    public static ClassMetaInfo buildClassMetaInfo(Class<?> clazz) {
        if(null != cache.get(clazz)) return cache.get(clazz);

        ClassMetaInfo classMetaInfo = new ClassMetaInfo();
        classMetaInfo.setClazz(clazz);
        classMetaInfo.initConfig(clazz);

        List<FieldMetaInfo> magicFields = buildAllMagicField(classMetaInfo);
        int total = magicFields.stream().mapToInt(FieldMetaInfo::getTotalBytes).sum();
        AssertUtil.assertTotalLengthNotZero(total, classMetaInfo);
        if(total == 0) {
            return null;
        }

        classMetaInfo.setTotalBytes(total);
        classMetaInfo.setFields(magicFields);

        cache.put(classMetaInfo.getClazz(), classMetaInfo);
        return classMetaInfo;
    }


    /**
     * 包装所有有标记为 @MagicField 的字段
     * @param c
     * @return
     */
    private static  List<FieldMetaInfo>  buildAllMagicField(ClassMetaInfo classMetaInfo) {
        List<FieldMetaInfo> res = new ArrayList<>(50);
        Field[] fields = classMetaInfo.getClazz().getDeclaredFields();
        for (Field field : fields) {
            MagicField magicField = field.<MagicField>getAnnotation(MagicField.class);
            if (Objects.nonNull(magicField)) {
                FieldMetaInfo fieldMetaInfo = buildFieldMetaInfo(field, magicField);
                AssertUtil.assertFieldMetaInfoNotNull(fieldMetaInfo, field, classMetaInfo);
                if(Objects.nonNull(fieldMetaInfo)) {
                    res.add(fieldMetaInfo);
                }
            }
        }
        res.sort((o1,o2) ->  o1.getMagicField().order() - o2.getMagicField().order());

        AssertUtil.assertFieldsSortIsRight(res);
        return res;
    }


    /**
     * 将单独的 Field 包装为 FieldMetaInfo
     * @param field
     * @param magicField
     * @return
     */
    private static FieldMetaInfo buildFieldMetaInfo(Field field, MagicField magicField) {
        FieldMetaInfo fieldMetaInfo = new FieldMetaInfo();
        fieldMetaInfo.setField(field);
        fieldMetaInfo.setMagicField(magicField);
        fieldMetaInfo.setSize(magicField.size());
        fieldMetaInfo.setCharset(magicField.charset());

        field.setAccessible(true);
        Class<?> type = field.getType();
        TypeEnum typeEnum = TypeEnum.getType(type);

        fieldMetaInfo.setType(typeEnum);

        Class<?> genericClazz = null;
        Type genericType = null;
        int size = 0;
        switch (typeEnum) {
            case BYTE:
            case CHAR:
            case BOOLEAN:
            case SHORT:
            case INT:
            case FLOAT:
            case DOUBLE:
            case LONG:
                size = typeEnum.getBytes();
                fieldMetaInfo.setSize(size);
                fieldMetaInfo.setClazz(fieldMetaInfo.getField().getType());
                break;
            case STRING:
                AssertUtil.assertSizeNotNull(fieldMetaInfo);
                size = fieldMetaInfo.getMagicField().size();
                fieldMetaInfo.setClazz(String.class);
                fieldMetaInfo.setSize(size);
                break;
            case ARRAY:
                genericClazz = fieldMetaInfo.getField().getType().getComponentType();
                size = fieldMetaInfo.getSize() * calcCollectionSize(genericClazz);
                if(0 == size) genericClazz = null;

                fieldMetaInfo.setClazz(genericClazz);
                break;
            case LIST:
                genericType = fieldMetaInfo.getField().getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                }

                size = fieldMetaInfo.getSize() * calcCollectionSize(genericClazz);
                if(size == 0) genericClazz = null;

                fieldMetaInfo.setClazz(genericClazz);
                fieldMetaInfo.setSize(fieldMetaInfo.getSize());
                break;
            case OBJECT:
                ClassMetaInfo classMetaInfo = buildClassMetaInfo(type);
                if(Objects.isNull(classMetaInfo)) {
                    break;
                }
                size = classMetaInfo.getTotalBytes();
                fieldMetaInfo.setSize(size);
                fieldMetaInfo.setClazz(type);
                break;
        }

        fieldMetaInfo.setTotalBytes(size);

        if(Objects.isNull(fieldMetaInfo.getClazz()) || 0 == fieldMetaInfo.getTotalBytes()) {
           fieldMetaInfo = null;
        }

        return fieldMetaInfo;
    }

    /**
     * 取集合泛型的数据大小
     * @param genericClazz
     * @return
     */
    private static int calcCollectionSize(Class<?> genericClazz) {
        TypeEnum typeEnum = TypeEnum.getType(genericClazz);
        int res = 0;
        switch (typeEnum) {
            case BYTE:
            case CHAR:
            case BOOLEAN:
            case SHORT:
            case INT:
            case FLOAT:
            case DOUBLE:
            case LONG:
                res = typeEnum.getBytes();
                break;
            case OBJECT:
                ClassMetaInfo classMetaInfo = buildClassMetaInfo(genericClazz);
                if(Objects.nonNull(classMetaInfo)) {
                   res = classMetaInfo.getTotalBytes();
                }
                break;
        }
        return res;
    }


}
