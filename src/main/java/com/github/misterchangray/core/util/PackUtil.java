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


    public static <T> T packObject(byte[] bytes, Class<?> clazz) {
        ClassMetaInfo classMetaInfo = ClassMetaInfoUtil.buildClassMetaInfo(clazz);
        if(Objects.isNull(classMetaInfo)) {
            return null;
        }
        ByteBuffer res = ByteBuffer.allocate(bytes.length).order(classMetaInfo.getByteOrder());
        res.put(bytes);
        res.position(0);


        T object = null;
        try {
            object = (T) clazz.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException e) {
            throw new MagicByteException(String.format("class must be declared public; inner class is not supported; \r\n%s", e));
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new MagicByteException(String.format("no public and no arguments constructor; \r\n%s", e));
        }


        for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            decodeField(object, fieldMetaInfo, res, classMetaInfo);
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
        boolean interrupt = fieldMetaInfo.getTotalBytes() > data.capacity() - data.position();
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
                int len = fieldMetaInfo.getTotalBytes();
                if(fieldMetaInfo.isDynamic()) {
                    int order = fieldMetaInfo.getMagicField().dynamicSizeOf();
                    len = convertToInt(object,  classMetaInfo.getByOrderId(order));
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
                    count = convertToInt(object,  classMetaInfo.getByOrderId(order));
                }

                Object array = Array.newInstance(fieldMetaInfo.getClazz(), count);
                for(int i=0, unitBytes = fieldMetaInfo.getElementBytes(); i<count; i++) {
                    TypeEnum typeEnum  = TypeEnum.getType(fieldMetaInfo.getClazz());
                    if(TypeEnum.OBJECT == typeEnum) {
                        bytes = new byte[unitBytes];
                        data.get(bytes);
                        if(fieldMetaInfo.isAutoTrim() && ClassUtil.isEmptyData(bytes)) continue;

                        Array.set(array, i, packObject(bytes, fieldMetaInfo.getClazz()));
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
                    count = convertToInt(object,  classMetaInfo.getByOrderId(order));
                }

                List<Object> list = new ArrayList<>(count);
                for(int i=0, unitBytes = fieldMetaInfo.getElementBytes(); i<count; i++) {
                    TypeEnum typeEnum  = TypeEnum.getType(fieldMetaInfo.getClazz());
                    if(TypeEnum.OBJECT == typeEnum) {
                        bytes = new byte[unitBytes];
                        data.get(bytes);
                        if(classMetaInfo.isAutoTrim() && ClassUtil.isEmptyData(bytes)) continue;

                        list.add( packObject(bytes, fieldMetaInfo.getClazz()));
                    } else {
                        list.add(getBaseFieldValue(typeEnum, data));
                    }
                }

                ClassUtil.setValue(object, list, fieldMetaInfo.getField());
                break;
            case OBJECT:
                bytes = new byte[fieldMetaInfo.getTotalBytes()];
                data.get(bytes);
                ClassUtil.setValue(object, packObject(bytes, fieldMetaInfo.getClazz()), fieldMetaInfo.getField());
                break;
        }

    }


    public static Integer convertToInt(Object object, FieldMetaInfo field) {
        Object o =  ClassUtil.readValue(object, field.getField());

        switch (field.getType()) {
            case BYTE:
                Byte b = (Byte) o;
                return (int) b.byteValue();
            case SHORT:
                Short s = (Short) o;
                return (int) s.shortValue();
            case INT:
                Integer integer = (Integer) o;
                return (int) integer.intValue();
        }
        return 0;
    }

}
