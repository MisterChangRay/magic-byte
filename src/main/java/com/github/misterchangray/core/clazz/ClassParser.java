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
            if(!this.beforeVerify(field)) {
                continue;
            }
            field.setAccessible(true);

            FieldMetaInfo fieldMetaInfo = new FieldMetaInfo();
            this.linkField(field, fieldMetaInfo, classMetaInfo);
            if(!this.afterVerify(fieldMetaInfo)) {
                continue;
            }

            classMetaInfo.getFields().add(fieldMetaInfo);
        }

        this.afterLinkClazz(classMetaInfo, clazz);
        return classMetaInfo;
    }

    private boolean beforeVerify(Field field) {
        MagicField magicField = AnnotationUtil.getMagicFieldAnnotation(field);
        return Objects.nonNull(magicField);
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
    private boolean afterVerify(FieldMetaInfo field) {
        // 序号重复
        if(Objects.nonNull(field.getOwnerClazz().getFieldMetaInfoByOrderId(field.getOrderId()))) {
            throw new MagicParseException("Sorting cannot be repeated; at:" + field.getFullName());
        }

        // dynamicSize 和 size 不能共存
        if(field.getMagicField().size() > 0 && field.getMagicField().dynamicSizeOf() > 0) {
            throw new PropertiesInvalidException("size or dynamicSize only can be use one, at:" + field.getFullName());
        }

        // list string array 必须配置 size or dynamicSize
        if(TypeManager.isVariable(field.getType()) && field.getMagicField().size() <= 0 && field.getMagicField().dynamicSizeOf() < 0) {
            throw new NotYetConfigurationSizeException("size or dynamicSize must be use one, at:" + field.getFullName());
        }

        if(field.isDynamic()) {
            // dynamicSize 必须引用申明在前面的变量
            if(Objects.isNull(field.getDynamicRef())) {
                throw new DynamicOfInvalidException("dynamicSizeOf property value should be less than itself order, at:" + field.getFullName());
            }

            // dynamicSize 引用数据类型只能为 byte, short, int
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
        this.initField(field, fieldMetaInfo, classMetaInfo, field.getType());
        this.copyConfiguration(field, fieldMetaInfo, classMetaInfo);

        if(TypeManager.isCollection(fieldMetaInfo.getType())) {
            fieldMetaInfo.setGenericsField(this.newGenericsField(fieldMetaInfo));
            fieldMetaInfo.setElementBytes(fieldMetaInfo.getGenericsField().getElementBytes());
        }

    }

    private void initField(Field field, FieldMetaInfo fieldMetaInfo, ClassMetaInfo classMetaInfo, Class<?> clazz) {
        fieldMetaInfo.setField(field);
        fieldMetaInfo.setFullName(classMetaInfo.getFullName() + "." + field.getName());
        fieldMetaInfo.setOwnerClazz(classMetaInfo);
        fieldMetaInfo.setAutoTrim(fieldMetaInfo.getOwnerClazz().isAutoTrim());
        fieldMetaInfo.setFillByte(fieldMetaInfo.getOwnerClazz().getFillByte());
        fieldMetaInfo.setSize(1);
        fieldMetaInfo.setClazz(clazz);
        TypeEnum type = TypeManager.getType(clazz);
        fieldMetaInfo.setType(type);
        fieldMetaInfo.setElementBytes(type.getBytes());
        fieldMetaInfo.setWriter(TypeManager.newWriter(fieldMetaInfo));
        fieldMetaInfo.setReader(TypeManager.newReader(fieldMetaInfo));
    }


    /**
     * 泛型的字段为虚拟字段
     *
     * @param fieldMetaInfo
     * @return
     */
    private FieldMetaInfo newGenericsField(FieldMetaInfo origin) {
        FieldMetaInfo fieldMetaInfo = new FieldMetaInfo();
        Class<?> clazz = null;
        if(origin.getType() == TypeEnum.ARRAY) {
            if(origin.getField().getType().getName().startsWith("[[")) {
                throw new MagicParseException("not support matrix, such as int[][], at: " + origin.getFullName());
            }
            clazz = origin.getField().getType().getComponentType();
        }


        Type genericType = origin.getField().getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            if(pt.getActualTypeArguments()[0] instanceof ParameterizedType) {
                throw new MagicParseException("not support matrix, such as List<List<String>>, at: " + fieldMetaInfo.getFullName());
            }
            clazz =(Class<?>)pt.getActualTypeArguments()[0];

        }
        origin.setElementBytes(fieldMetaInfo.getElementBytes());
        fieldMetaInfo.setClazz(clazz);
        this.initField(origin.getField(), fieldMetaInfo, origin.getOwnerClazz(), fieldMetaInfo.getClazz());
        return fieldMetaInfo;
    }

    private void copyConfiguration(Field field, FieldMetaInfo fieldMetaInfo, ClassMetaInfo classMetaInfo) {
        MagicField magicField = AnnotationUtil.getMagicFieldAnnotation(field);
        if(Objects.isNull(magicField)) return;

        fieldMetaInfo.setMagicField(magicField);
        fieldMetaInfo.setOrderId(magicField.order());
        fieldMetaInfo.setAutoTrim(magicField.autoTrim() || classMetaInfo.isAutoTrim());
        fieldMetaInfo.setCharset(magicField.charset());
        fieldMetaInfo.setFillByte(magicField.fillByte());
        if(magicField.size() > 0) {
            fieldMetaInfo.setSize(magicField.size());
        }
        fieldMetaInfo.setDynamicSizeOf(magicField.dynamicSizeOf());

        if( fieldMetaInfo.getDynamicSizeOf() > 0 ){
            fieldMetaInfo.setDynamic(true);
            classMetaInfo.setDynamic(true);
            FieldMetaInfo dynamicRef =
                    fieldMetaInfo.getOwnerClazz().getFieldMetaInfoByOrderId(fieldMetaInfo.getDynamicSizeOf());
            if(Objects.isNull(dynamicRef)) {
                return;
            }
            fieldMetaInfo.setDynamicRef(dynamicRef);
            dynamicRef.setDynamicRef(fieldMetaInfo);
        }
    }


}
