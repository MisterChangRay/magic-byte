package com.github.misterchangray.core.clazz;


import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.*;
import com.github.misterchangray.core.util.AnnotationUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteOrder;
import java.text.ParseException;
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
        this.beforeLinkClazz(classMetaInfo, clazz);

        for (Field field : clazz.getDeclaredFields()) {
            FieldMetaInfo fieldMetaInfo = new FieldMetaInfo();
            this.linkField(field, fieldMetaInfo, classMetaInfo);
            if(this.verify(fieldMetaInfo)) continue;

            classMetaInfo.getFields().add(fieldMetaInfo);
        }

        this.afterLinkClazz(classMetaInfo, clazz);
        return classMetaInfo;
    }

    /**
     * 统计总字节数
     * @param classMetaInfo
     * @param clazz
     */
    private void afterLinkClazz(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        int total = classMetaInfo.getFields().stream().mapToInt(item -> item.getElementBytes() * item.getSize()).sum();
        classMetaInfo.setElementBytes(total);
    }

    private void beforeLinkClazz(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        classMetaInfo.setClazz(clazz);
        classMetaInfo.setFullName(clazz.getName());
        classMetaInfo.setByteOrder(ByteOrder.BIG_ENDIAN);
        classMetaInfo.setFields(new ArrayList<>());

        MagicClass magicClass = AnnotationUtil.getMagicClassAnnotation(clazz);
        if(Objects.isNull(magicClass)) return;

        classMetaInfo.setAutoTrim(magicClass.autoTrim());
        classMetaInfo.setByteOrder(magicClass.byteOrder().getBytes());
        classMetaInfo.setFillByte(magicClass.fillByte());
    }

    /**
     * 字段校验
     * @param field
     * @return
     */
    private boolean verify(FieldMetaInfo field) {
        if(field.getSize() > 0 && field.getDynamicSizeOf() > 0) {
            throw new PropertiesInvalidException("size or dynamicSize only can be use one, at:" + field.getFullName());
        }

        if(!field.isDynamic() && field.getSize() < 0) {
            throw new NotYetConfigurationSizeException("please configuration size properties, at:" + field.getFullName());
        }

        if(field.isDynamic()) {
            if(Objects.isNull(field.getDynamicRef())) {
                throw new DynamicOfInvalidException("dynamicSizeOf property value should be less than itself order, at:" + field.getFullName());
            }
            FieldMetaInfo dynamicRef = field.getDynamicRef();
            if(dynamicRef.getType() != TypeEnum.BYTE &&
                    dynamicRef.getType() != TypeEnum.SHORT &&
                    dynamicRef.getType() != TypeEnum.INT) {
                throw new DynamicRefInvalidException("dynamic target type must be primitive and only be byte, short, int; at:" + field.getFullName());
            }
        }


        return true;
    }

    private void linkField(Field field, FieldMetaInfo fieldMetaInfo, ClassMetaInfo classMetaInfo) {
        fieldMetaInfo.setField(field);
        fieldMetaInfo.setFullName(classMetaInfo.getFullName() + "." + field.getName());
        fieldMetaInfo.setOwnerClazz(classMetaInfo);
        fieldMetaInfo.setAutoTrim(fieldMetaInfo.getOwnerClazz().isAutoTrim());
        fieldMetaInfo.setFillByte(fieldMetaInfo.getOwnerClazz().getFillByte());
        fieldMetaInfo.setClazz(field.getType());
        fieldMetaInfo.setGenericsType(field.getType());
        this.copyConfiguration(field, fieldMetaInfo, classMetaInfo);

        TypeEnum type = TypeEnum.getType(field.getType());
        fieldMetaInfo.setType(type);
        classMetaInfo.setElementBytes(type.getBytes());
        fieldMetaInfo.setGenericsType(this.parseGenericsType(fieldMetaInfo));
        switch (type) {
            case ARRAY:
            case LIST:
            case OBJECT:
                ClassMetaInfo parse = parse(fieldMetaInfo.getGenericsType());
                classMetaInfo.setElementBytes(parse.getElementBytes());

        }
    }

    /**
     * 获取
     * @param fieldMetaInfo
     * @return
     */
    private Class<?> parseGenericsType(FieldMetaInfo fieldMetaInfo) {
        if(fieldMetaInfo.getType() != TypeEnum.ARRAY &&
                fieldMetaInfo.getType() != TypeEnum.LIST) {
            return fieldMetaInfo.getGenericsType();
        }

        if(fieldMetaInfo.getType() == TypeEnum.ARRAY) {
            if(fieldMetaInfo.getField().getType().getName().startsWith("[[+")) {
                throw new MagicParseException("not support matrix, such as int[][], at: " + fieldMetaInfo.getFullName());
            }
            return fieldMetaInfo.getField().getType().getComponentType();
        }


        Type genericType = fieldMetaInfo.getField().getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            if(pt.getActualTypeArguments()[0] instanceof ParameterizedType) {
                throw new MagicParseException("not support matrix, such as List<List<String>>, at: " + fieldMetaInfo.getFullName());
            }

            return  (Class<?>)pt.getActualTypeArguments()[0];
        }

        return null;
    }

    private void copyConfiguration(Field field, FieldMetaInfo fieldMetaInfo, ClassMetaInfo classMetaInfo) {
        MagicField magicField = AnnotationUtil.getMagicFieldAnnotation(field);
        if(Objects.isNull(magicField)) return;

        fieldMetaInfo.setMagicField(magicField);
        fieldMetaInfo.setOrderId(magicField.order());
        fieldMetaInfo.setAutoTrim(magicField.autoTrim());
        fieldMetaInfo.setCharset(magicField.charset());
        fieldMetaInfo.setFillByte(magicField.fillByte());
        fieldMetaInfo.setSize(magicField.size());
        fieldMetaInfo.setDynamicSizeOf(magicField.dynamicSizeOf());

        if(fieldMetaInfo.getDynamicSizeOf() > 0){
            fieldMetaInfo.setDynamic(true);
            classMetaInfo.setDynamic(true);
            FieldMetaInfo dynamicRef =
                    ClassManager.getFieldMetaInfoByOrderId(classMetaInfo.getClazz(), fieldMetaInfo.getDynamicSizeOf());
            if(Objects.isNull(dynamicRef)) {
                return;
            }
            fieldMetaInfo.setDynamicRef(dynamicRef);
            dynamicRef.setDynamicRef(fieldMetaInfo);
        }
    }


}