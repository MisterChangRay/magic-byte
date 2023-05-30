package com.github.misterchangray.core.clazz;


import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.intf.MConverter;
import com.github.misterchangray.core.util.AnnotationUtil;
import com.github.misterchangray.core.util.ExceptionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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


    public ClassMetaInfo parse(ClassMetaInfo classMetaInfo) {
        Class clazz  = classMetaInfo.getClazz();
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

        this.linkCustomConverter(classMetaInfo, clazz);
        this.copyConfiguration(classMetaInfo, clazz);
    }

    /**
     * 将自定义的注解注册到类型管理器中
     * @param classMetaInfo
     * @param clazz
     */
    private void linkCustomConverter(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        MagicConverter magicConverter = AnnotationUtil.getMagicClassConverterAnnotation(clazz);
        if(Objects.nonNull(magicConverter)) {
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

            classMetaInfo.setCustomConverter(magicConverterInfo);

            FieldMetaInfo virtualField = new FieldMetaInfo();
            virtualField.setClazz(clazz);
            virtualField.setType(TypeEnum.CUSTOM);
            virtualField.setCustomConverter(magicConverterInfo);
            classMetaInfo.setWriter(TypeManager.newWriter(virtualField));
            classMetaInfo.setReader(TypeManager.newReader(virtualField));
            if(magicConverter.fixSize() > 0) {
                virtualField.setElementBytes(magicConverter.fixSize());
                classMetaInfo.setElementBytes(magicConverter.fixSize());
            } else {
                classMetaInfo.setElementBytes(1);
                classMetaInfo.setDynamic(true);
            }
        }
    }


    /**
     * 收尾工作
     * - 统计总字节数
     * - 一些类型的校验
     * - 将字段维护到到线性字段中
     * -
     * @param classMetaInfo
     * @param clazz
     */
    private void afterLinkClazz(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        if(Objects.nonNull(classMetaInfo.getFields()) && classMetaInfo.getFields().size() == 0) return;

        int
                totalBytes = 0, // 总字节
                fieldBytes = 0
        ;
        classMetaInfo.getFields().sort((Comparator.comparingInt(FieldMetaInfo::getOrderId)));
        for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            fieldBytes =  fieldMetaInfo.getElementBytes() * fieldMetaInfo.getSize();


            verifyDynamicSizeOf(fieldMetaInfo);

            totalBytes += fieldBytes;
        }
        if(totalBytes > classMetaInfo.getElementBytes()) {
            classMetaInfo.setElementBytes(totalBytes);
        }
        classMetaInfo.getRoot().getFlatFields().addAll(classMetaInfo.getFields());

    }

    private boolean verifyDynamicSizeOf(FieldMetaInfo fieldMetaInfo) {
        if(fieldMetaInfo.isDynamic() && fieldMetaInfo.isDynamicSizeOf()){
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
                    dynamicRef.getType() != TypeEnum.INT &&
                    dynamicRef.getType() != TypeEnum.UBYTE &&
                    dynamicRef.getType() != TypeEnum.USHORT &&
                    dynamicRef.getType() != TypeEnum.UINT) {
                throw new InvalidParameterException("dynamic refs the type of filed must be primitive and only be byte, short, int; at: " + fieldMetaInfo.getFullName());
            }

            return true;
        }

        return false;
    }

    private void copyConfiguration(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        MagicClass magicClass = AnnotationUtil.getMagicClassAnnotation(clazz);
        if(Objects.nonNull(magicClass)) {
            classMetaInfo.setByteOrder(magicClass.byteOrder().getBytes());
            classMetaInfo.setStrict(magicClass.strict());
        }
    }

}
