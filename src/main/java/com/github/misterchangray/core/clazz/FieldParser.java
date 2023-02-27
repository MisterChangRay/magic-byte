package com.github.misterchangray.core.clazz;


import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.*;
import com.github.misterchangray.core.intf.MConverter;
import com.github.misterchangray.core.util.AnnotationUtil;
import com.github.misterchangray.core.util.ExceptionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Objects;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-21 15:18
 **/
public class FieldParser {
    private static FieldParser fieldParser;

    public static FieldParser getInstance() {
        if(null == fieldParser) {
            fieldParser = new FieldParser();
        }
        return fieldParser;
    }


    public FieldMetaInfo parse(Field field, ClassMetaInfo classMetaInfo) {
        if(!beforeVerify(field)) {
            return null;
        }
        FieldMetaInfo fieldMetaInfo = new FieldMetaInfo();
        linkField(field, fieldMetaInfo, classMetaInfo);
        this.linkCustomConverter(field, fieldMetaInfo);

        afterVerify(fieldMetaInfo);

        field.setAccessible(true);
        return fieldMetaInfo;
    }

    private boolean beforeVerify(Field field) {
        MagicField magicField = AnnotationUtil.getMagicFieldAnnotation(field);
        MagicConverter converter = AnnotationUtil.getMagicFieldConverterAnnotation(field);
        return Objects.nonNull(magicField) || Objects.nonNull(converter);
    }


    /**
     * 字段校验
     * @param field
     * @return
     */
    private void afterVerify(FieldMetaInfo field) {
        // 序号重复
        if(Objects.nonNull(field.getOwnerClazz().getFieldMetaInfoByOrderId(field.getOrderId()))) {
            throw new InvalidParameterException("Sorting cannot be repeated; at: " + field.getFullName());
        }

        // dynamicSize 和 size 不能共存
        if(field.getMagicField().size() > 0 && field.getMagicField().dynamicSizeOf() > 0) {
            throw new InvalidParameterException("size or dynamicSize only can be use one; at: " + field.getFullName());
        }

        // list string array 必须配置 size or dynamicSize
        if(TypeManager.isVariable(field.getType()) && field.getMagicField().size() <= 0 && field.getMagicField().dynamicSizeOf() < 0) {
            throw new InvalidParameterException("not yet configuration size or dynamicSize of the field; at: " + field.getFullName());
        }

        // dynamicSize only use the list string and array
        if(!TypeManager.isVariable(field.getType()) && field.getMagicField().dynamicSizeOf() > 0) {
            throw new InvalidParameterException("dynamicSize only use the list string and array; at: " + field.getFullName());
        }

        // dynamicSize only use the list string and array
        if(!TypeManager.isVariable(field.getType()) && field.getMagicField().dynamicSize()) {
            throw new InvalidParameterException("dynamicSize only use the list string and array; at: " + field.getFullName());
        }

        // dynamicSize 必须和 size 属性共同使用
        if(field.getMagicField().size() <= 0 && field.getMagicField().dynamicSize()) {
            throw new InvalidParameterException("dynamicSize must use with size properties; at: " + field.getFullName());
        }

        // calcLength only use byte, short, int
        verifyCalcLength(field);

        // checkCode only use byte, short, int ,long
        verifyCalcCheckCode(field);
    }


    private void verifyCalcCheckCode(FieldMetaInfo fieldMetaInfo) {
        if(fieldMetaInfo.isCalcCheckCode()) {
            if(fieldMetaInfo.getType() != TypeEnum.BYTE &&
                    fieldMetaInfo.getType() != TypeEnum.SHORT &&
                    fieldMetaInfo.getType() != TypeEnum.INT &&
                    fieldMetaInfo.getType() != TypeEnum.LONG) {
                throw new InvalidParameterException("calcCheckCode field the type must be primitive and only be byte, short, int, long; at: " + fieldMetaInfo.getFullName());
            }
        }

    }

    private void verifyCalcLength(FieldMetaInfo fieldMetaInfo) {
        if(fieldMetaInfo.isCalcLength()) {
            if(fieldMetaInfo.getType() != TypeEnum.BYTE &&
                    fieldMetaInfo.getType() != TypeEnum.SHORT &&
                    fieldMetaInfo.getType() != TypeEnum.INT) {
                throw new InvalidParameterException("calcLength field the type must be primitive and only be byte, short, int; at: " + fieldMetaInfo.getFullName());
            }
        }
    }


    private void linkField(Field field, FieldMetaInfo fieldMetaInfo, ClassMetaInfo classMetaInfo) {
        fieldMetaInfo.setField(field);
        fieldMetaInfo.setFullName(classMetaInfo.getFullName() + "." + field.getName());
        fieldMetaInfo.setOwnerClazz(classMetaInfo);
        fieldMetaInfo.setClazz(field.getType());

        this.copyConfiguration(field, fieldMetaInfo, classMetaInfo);

        this.initField(fieldMetaInfo, classMetaInfo, field.getType());

        if(TypeManager.isCollection(fieldMetaInfo.getType())) {
            fieldMetaInfo.setGenericsField(this.newGenericsField(fieldMetaInfo));
            fieldMetaInfo.getGenericsField().setCustomConverter(fieldMetaInfo.getCustomConverter());
            fieldMetaInfo.setElementBytes(fieldMetaInfo.getGenericsField().getElementBytes());
        }

    }


    private void initField( FieldMetaInfo fieldMetaInfo, ClassMetaInfo classMetaInfo, Class<?> clazz) {
        fieldMetaInfo.setType(TypeManager.getType(clazz));
        ClassMetaInfo fieldClassMetaInfo = ClassManager.getClassFieldMetaInfo(clazz, classMetaInfo);
        fieldMetaInfo.setClazzMetaInfo(fieldClassMetaInfo);
        fieldClassMetaInfo.setParent(classMetaInfo);
        fieldMetaInfo.setElementBytes(fieldClassMetaInfo.getElementBytes());
        // set parent isDynamic flag if the child true
        if(fieldClassMetaInfo.isDynamic()) {
            fieldMetaInfo.setDynamic(true);
            classMetaInfo.setDynamic(true);
        }
        if(classMetaInfo.isStrict()) {
            fieldClassMetaInfo.setStrict(true);
        }
        fieldMetaInfo.setWriter(TypeManager.newWriter(fieldMetaInfo));
        fieldMetaInfo.setReader(TypeManager.newReader(fieldMetaInfo));

        if(fieldMetaInfo.getSize() < 0) {
            fieldMetaInfo.setSize(1);
        }

        if(fieldMetaInfo.getDynamicSizeOf() > 0 ){
            fieldMetaInfo.setSize(0);
            fieldMetaInfo.setDynamic(true);
            classMetaInfo.setDynamic(true);
        }

        if(Objects.nonNull(fieldMetaInfo.getCustomConverter()) &&
                !fieldMetaInfo.getCustomConverter().isFixsize()) {
            fieldMetaInfo.setElementBytes(1);
            fieldMetaInfo.setDynamic(true);
            classMetaInfo.setDynamic(true);
        }

    }


    /**
     * 将自定义的注解注册到类型管理器中
     * @param field
     * @param fieldMetaInfo
     */
    private void linkCustomConverter(Field field, FieldMetaInfo fieldMetaInfo) {
        MagicConverter magicConverter = AnnotationUtil.getMagicFieldConverterAnnotation(field);
        if(Objects.isNull(magicConverter)) return;

        Class clazz = field.getType();
        if(TypeManager.isCollection(TypeManager.getType(clazz))) {
            clazz = TypeManager.getGenericsFieldType(fieldMetaInfo);
        } else {
            fieldMetaInfo.setType(TypeEnum.CUSTOM);
            fieldMetaInfo.setWriter(TypeManager.newWriter(fieldMetaInfo));
            fieldMetaInfo.setReader(TypeManager.newReader(fieldMetaInfo));
            if(magicConverter.fixSize() == -1) {
                fieldMetaInfo.getOwnerClazz().setDynamic(true);
            } else {
                fieldMetaInfo.setElementBytes(magicConverter.fixSize());
            }
        }

        MConverter mConverter = null;
        try {
            mConverter = magicConverter.converter().getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException ae) {
            ExceptionUtil.throwIllegalAccessException(magicConverter.converter());
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            ExceptionUtil.throwInstanceErrorException(magicConverter.converter());
        }

        CustomConverterInfo magicConverterInfo =
                new CustomConverterInfo(magicConverter.attachParams(), mConverter, magicConverter.fixSize());

        fieldMetaInfo.setCustomConverter(magicConverterInfo);
    }


    /**
     * 泛型的字段为虚拟字段
     *
     * @param origin
     * @return
     */
    private FieldMetaInfo newGenericsField(FieldMetaInfo origin) {
        FieldMetaInfo fieldMetaInfo = new FieldMetaInfo();
        Class<?> clazz = TypeManager.getGenericsFieldType(origin);
        fieldMetaInfo.setClazz(clazz);
        this.copyConfiguration(origin.getField(), fieldMetaInfo, origin.getOwnerClazz());
        this.initField( fieldMetaInfo, origin.getOwnerClazz(), fieldMetaInfo.getClazz());

        if(fieldMetaInfo.getType() == TypeEnum.STRING) {
            throw new InvalidTypeException("not support String with collection, such as List<String> or String[]; at: " + fieldMetaInfo.getFullName());
        }

        return fieldMetaInfo;
    }

    private void copyConfiguration(Field field, FieldMetaInfo fieldMetaInfo, ClassMetaInfo classMetaInfo) {
        MagicField magicField = AnnotationUtil.getMagicFieldAnnotation(field);
        if(Objects.isNull(magicField)) return;

        fieldMetaInfo.setMagicField(magicField);
        fieldMetaInfo.setOrderId(magicField.order());
        Charset charset = null;
        try {
            charset = Charset.forName(magicField.charset());
        } catch (UnsupportedCharsetException ae) {
            ExceptionUtil.throwIllegalCharset(fieldMetaInfo);
        }
        fieldMetaInfo.setCharset(charset);
        fieldMetaInfo.setDynamicSize(magicField.dynamicSize());
        fieldMetaInfo.setCalcCheckCode(magicField.calcCheckCode());
        fieldMetaInfo.setCalcLength(magicField.calcLength());
        fieldMetaInfo.setTimestampFormatter(magicField.timestampFormat());

        if(magicField.defaultVal() > 0){
            fieldMetaInfo.setDefaultVal(magicField.defaultVal());
        }

        fieldMetaInfo.setSize(magicField.size());
        fieldMetaInfo.setDynamicSizeOf(magicField.dynamicSizeOf());

    }


}
