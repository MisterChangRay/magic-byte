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
 *
 * 类解析,解析注解及配置
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

            classMetaInfo.addField(fieldMetaInfo);
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
            virtualField.setCustomType(TypeManager.getType(clazz));
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

        this.initAccessInfo(classMetaInfo, "");

        int
                totalBytes = 0, // 总字节
                fieldBytes = 0
        ;
        classMetaInfo.getFields().sort((Comparator.comparingInt(FieldMetaInfo::getOrderId)));
        for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            fieldBytes =  fieldMetaInfo.getElementBytes() * fieldMetaInfo.getSize();

            totalBytes += fieldBytes;
        }
        if(totalBytes > classMetaInfo.getElementBytes()) {
            classMetaInfo.setElementBytes(totalBytes);
        }

    }

    /**
     * 递归解析当前类的所有属性
     * 即每个类解析完成后，只有当前类的所有属性的访问路径
     * @param classMetaInfo
     * @param accessPath
     */
    private void initAccessInfo(ClassMetaInfo classMetaInfo, String accessPath) {
        if(Objects.isNull(classMetaInfo.getFields()) || classMetaInfo.getFields().size() == 0) {
            return;
        }

        if(accessPath.length() > 0) {
            accessPath += '.';
        }
        for (FieldMetaInfo field : classMetaInfo.getFields()) {
            field.setAccessPath(accessPath  + field.getField().getName());
            initAccessInfo(field.getClazzMetaInfo(), field.getAccessPath());
        }

    }


    private void copyConfiguration(ClassMetaInfo classMetaInfo, Class<?> clazz) {
        MagicClass magicClass = AnnotationUtil.getMagicClassAnnotation(clazz);
        if(Objects.nonNull(magicClass)) {
            // 如果注解里端序用的 AUTO 就用全局默认的，否则用注解里设置的
            if (magicClass.byteOrder() == com.github.misterchangray.core.enums.ByteOrder.AUTO) {
                classMetaInfo.setByteOrder(GlobalConfigs.getGlobalDefaultByteOrder().getBytes());
            } else {
                classMetaInfo.setByteOrder(magicClass.byteOrder().getBytes());
            }
            classMetaInfo.setStrict(magicClass.strict());
        }
    }

}
