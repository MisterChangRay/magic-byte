package com.github.misterchangray.core.util;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.metainfo.ClassMetaInfo;
import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PackUtil {


    public static <T> T packObject(ByteBuffer data, Class<?> clazz) {
        ClassMetaInfo classMetaInfo = ClassMetaInfoUtil.buildClassMetaInfo(clazz);
        if(Objects.isNull(classMetaInfo)) {
            return null;
        }
        data.order(classMetaInfo.getByteOrder());
        T object = null;
        try {
            object = (T) clazz.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException e) {
            throw new MagicByteException(String.format("class must be declared public; inner class is not supported; \r\n%s", e));
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new MagicByteException(String.format("no public and no arguments constructor; \r\n%s", e));
        }


        for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            decodeField(object, fieldMetaInfo, data, classMetaInfo);
        }

        return object;
    }

    private static Object getBaseFieldValue(TypeEnum typeEnum,ByteBuffer data){
        switch (typeEnum) {
            case BYTE:
                return data.get();
            case CHAR:
               return (char)data.getChar();
            case BOOLEAN:
                return data.get() != 0;
            case SHORT:
               return data.getShort();
            case INT:
               return data.getInt();
            case FLOAT:
                return data.getFloat();
            case DOUBLE:
                return data.getDouble();
            case LONG:
               return data.getLong();
        }
        return null;
    }

    private static void decodeField(Object object, FieldMetaInfo fieldMetaInfo, ByteBuffer data, ClassMetaInfo classMetaInfo) {
        byte[] bytes = null;
        int count = 0;

        // 判断缓冲区异常, 部分数据解析, 数据不够则直接停止解析 java.nio.BufferUnderflowException
        boolean interrupt = fieldMetaInfo.getElementBytes() > data.capacity() - data.position();
        if(interrupt) {
            AssertUtil.assertDataError(interrupt, classMetaInfo);
            return;
        }

        switch (fieldMetaInfo.getType()) {
            case BYTE:
            case CHAR:
            case BOOLEAN:
            case SHORT:
            case INT:
            case FLOAT:
            case DOUBLE:
            case LONG:
                Object value = getBaseFieldValue(fieldMetaInfo.getType(), data);
                ClassUtil.setValue(object, value, fieldMetaInfo.getField());
                break;
            case STRING:
                int len = fieldMetaInfo.getElementBytes();
                if(fieldMetaInfo.isDynamic()) {
                    int order = fieldMetaInfo.getMagicField().dynamicSizeOf();
                    len = ClassUtil.readAsInt(classMetaInfo.getByOrderId(order), object);
                }
                bytes = new byte[len];
                data.get(bytes);
                String s = null;
                try {
                    s = new String(bytes, fieldMetaInfo.getCharset());
                } catch (UnsupportedEncodingException e) {
                    throw new MagicByteException(String.format("UnsupportedEncoding: %s", fieldMetaInfo.getCharset() ));
                }
                if(fieldMetaInfo.isAutoTrim()) s = s.trim();
                ClassUtil.setValue(object,s, fieldMetaInfo.getField());
                break;
            case ARRAY:
                count = fieldMetaInfo.getSize();
                if(fieldMetaInfo.isDynamic()) {
                    int order = fieldMetaInfo.getMagicField().dynamicSizeOf();
                    count = ClassUtil.readAsInt(classMetaInfo.getByOrderId(order), object);
                }

                Object array = Array.newInstance(fieldMetaInfo.getClazz(), count);
                for(int i=0, unitBytes = fieldMetaInfo.getElementBytes(); i<count; i++) {
                    TypeEnum typeEnum  = TypeEnum.getType(fieldMetaInfo.getClazz());
                    if(TypeEnum.OBJECT == typeEnum) {
                        Array.set(array, i, packObject(data, fieldMetaInfo.getClazz()));
                    } else {

                        Array.set(array, i, getBaseFieldValue(typeEnum, data));
                    }
                }

                ClassUtil.setValue(object, array, fieldMetaInfo.getField());
                break;
            case LIST:
                count = fieldMetaInfo.getSize();
                if(fieldMetaInfo.isDynamic()) {
                    int order = fieldMetaInfo.getMagicField().dynamicSizeOf();
                    count = ClassUtil.readAsInt(classMetaInfo.getByOrderId(order), object);
                }

                List<Object> list = new ArrayList<>(count);
                for(int i=0, unitBytes = fieldMetaInfo.getElementBytes(); i<count; i++) {
                    TypeEnum typeEnum  = TypeEnum.getType(fieldMetaInfo.getClazz());
                    if(TypeEnum.OBJECT == typeEnum) {
                        list.add( packObject(data, fieldMetaInfo.getClazz()));
                    } else {
                        list.add(getBaseFieldValue(typeEnum, data));
                    }
                }

                ClassUtil.setValue(object, list, fieldMetaInfo.getField());
                break;
            case OBJECT:
                ClassUtil.setValue(object, packObject(data, fieldMetaInfo.getClazz()), fieldMetaInfo.getField());
                break;
        }

    }



}
