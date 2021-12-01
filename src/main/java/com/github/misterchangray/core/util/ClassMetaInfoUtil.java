package com.github.misterchangray.core.util;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.metainfo.ClassMetaInfo;
import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.IntStream;

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
        classMetaInfo.setFields(new ArrayList<>());

        initAllMagicField(classMetaInfo);
        if(classMetaInfo.getFields().size() == 0) return  null;

        int[] ints = new int[classMetaInfo.getFields().get(classMetaInfo.getFields().size() - 1).getOrderId() + 1];
        for (FieldMetaInfo field : classMetaInfo.getFields()) {
            ints[field.getOrderId()] = field.getSize() * field.getElementBytes();
        }
        classMetaInfo.setDynamicSize(ints);

        int total = Arrays.stream(ints).sum();
        AssertUtil.assertTotalLengthNotZero(total, classMetaInfo);
        if(total == 0) {
            return null;
        }

        classMetaInfo.setElementBytes(total);
        cache.put(classMetaInfo.getClazz(), classMetaInfo);
        return classMetaInfo;
    }


    /**
     * 包装所有有标记为 @MagicField 的字段
     * @param c
     * @return
     */
    private static  void  initAllMagicField(ClassMetaInfo classMetaInfo) {
        List<FieldMetaInfo> res = classMetaInfo.getFields();
        Field[] fields = classMetaInfo.getClazz().getDeclaredFields();
        for (Field field : fields) {
            MagicField magicField = field.<MagicField>getAnnotation(MagicField.class);
            FieldMetaInfo fieldMetaInfo = new FieldMetaInfo();
            fieldMetaInfo.setOwnerClazz(classMetaInfo);
            fieldMetaInfo.setField(field);
            fieldMetaInfo.setMagicField(magicField);

            AssertUtil.assertHasMagicField(fieldMetaInfo);
            if (Objects.nonNull(magicField)) {
                fieldMetaInfo.setOrderId(magicField.order());

                boolean initRes = initFieldMetaInfo(fieldMetaInfo);
                AssertUtil.assertFieldMetaInfoInitSuccess(initRes, fieldMetaInfo);
                if(initRes) {
                    res.add(fieldMetaInfo);
                }
            }
        }
        res.sort((o1,o2) ->  o1.getMagicField().order() - o2.getMagicField().order());
        AssertUtil.assertFieldsSortIsRight(res);
    }


    /**
     * 将单独的 Field 包装为 FieldMetaInfo
     * @param field
     * @param magicField
     * @param classMetaInfo
     * @return
     */
    private static boolean initFieldMetaInfo(FieldMetaInfo fieldMetaInfo) {
        Field field = fieldMetaInfo.getField();
        MagicField magicField = fieldMetaInfo.getMagicField();
        fieldMetaInfo.setSize(magicField.size());
        fieldMetaInfo.setCharset(magicField.charset());
        fieldMetaInfo.setAutoTrim(magicField.autoTrim() || fieldMetaInfo.getOwnerClazz().isAutoTrim());
        AssertUtil.assertSizeOrDynamicOf(fieldMetaInfo);

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
                fieldMetaInfo.setSize(1);
                fieldMetaInfo.setElementBytes( typeEnum.getBytes());
                fieldMetaInfo.setClazz(fieldMetaInfo.getField().getType());
                break;
            case STRING:
                AssertUtil.assertEncoding(magicField.charset());
                AssertUtil.assertHasLength(fieldMetaInfo);
                size = fieldMetaInfo.getMagicField().size();
                fieldMetaInfo.setClazz(String.class);
                settingIfFiledIsDynamic(magicField, fieldMetaInfo);

                fieldMetaInfo.setSize(1);
                fieldMetaInfo.setElementBytes(size);
                break;
            case ARRAY:
                AssertUtil.assertHasLength(fieldMetaInfo);
                genericClazz = fieldMetaInfo.getField().getType().getComponentType();
                fieldMetaInfo.setElementBytes(calcCollectionSize(genericClazz));
                settingIfFiledIsDynamic(magicField, fieldMetaInfo);
                size = fieldMetaInfo.getSize();
                fieldMetaInfo.setSize(size);

                fieldMetaInfo.setClazz(genericClazz);
                break;
            case LIST:
                AssertUtil.assertHasLength(fieldMetaInfo);
                genericType = fieldMetaInfo.getField().getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                }

                fieldMetaInfo.setElementBytes(calcCollectionSize(genericClazz));
                settingIfFiledIsDynamic(magicField, fieldMetaInfo);
                size = fieldMetaInfo.getSize();

                fieldMetaInfo.setClazz(genericClazz);
                fieldMetaInfo.setSize(size);
                break;
            case OBJECT:
                ClassMetaInfo classMetaInfo = buildClassMetaInfo(type);
                if(Objects.isNull(classMetaInfo)) {
                    break;
                }
                size = classMetaInfo.getElementBytes();
                fieldMetaInfo.setSize(1);
                fieldMetaInfo.setElementBytes(size);
                fieldMetaInfo.setClazz(type);
                break;
        }

        if(Objects.isNull(fieldMetaInfo.getClazz())) {
           return false;
        }

        return true;
    }

    private static void settingIfFiledIsDynamic(MagicField magicField, FieldMetaInfo fieldMetaInfo) {
        if(magicField.dynamicSizeOf() > 0) {
            // dynamic field
            fieldMetaInfo.setDynamic(true);
            fieldMetaInfo.setDynamicRef(fieldMetaInfo.getOwnerClazz().getByOrderId(magicField.dynamicSizeOf()));

            fieldMetaInfo.getOwnerClazz().setDynamic(true);
        }
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
                   res = classMetaInfo.getElementBytes();
                }
                break;
        }
        return res;
    }


}
