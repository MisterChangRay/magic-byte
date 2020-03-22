package com.github.misterchangray.core.util;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.metainfo.ClassMetaInfo;
import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PackUtil {


    public static <T> T packObject(byte[] bytes, Class clazz) throws  InstantiationException, UnsupportedEncodingException {
        ClassMetaInfo classMetaInfo = ClassMetaInfoUtil.buildClassMetaInfo(clazz);
        ByteBuffer res = ByteBuffer.allocate(bytes.length).order(classMetaInfo.getByteOrder());
        res.put(bytes);
        res.position(0);


        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (IllegalAccessException e) {
            throw new MagicByteException(String.format("class must be declared public; inner class is not supported; \r\n%s", e));
        }

        int end = 0;
        end = classMetaInfo.getTotalBytes() > bytes.length ? bytes.length : classMetaInfo.getTotalBytes();

        for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            decodeField(object, fieldMetaInfo, res, classMetaInfo);
        }

        return (T) object;
    }

    private static Object getBaseFieldValue(TypeEnum typeEnum,ByteBuffer data){
        switch (typeEnum) {
            case BYTE:
                return data.get();
            case CHAR:
               return (char)data.getChar();
            case BOOLEAN:
                boolean res = data.get() == 0 ? false : true;
                return res;
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

    private static void decodeField(Object object, FieldMetaInfo fieldMetaInfo, ByteBuffer data, ClassMetaInfo classMetaInfo) throws UnsupportedEncodingException, InstantiationException {
        byte[] bytes = null;
        int count = 0;

        // 判断缓冲区异常, 部分数据解析, 数据不够则直接停止解析 java.nio.BufferUnderflowException
        if(fieldMetaInfo.getTotalBytes() > data.capacity() - data.position()) return;

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
                CalcUtil.setValue(object, value, fieldMetaInfo.getField());
                break;
            case STRING:
                bytes = new byte[fieldMetaInfo.getTotalBytes()];
                data.get(bytes);
                String s = new String(bytes, fieldMetaInfo.getCharset());
                if(classMetaInfo.isAutoTrim()) s = s.trim();
                CalcUtil.setValue(object,s, fieldMetaInfo.getField());
                break;
            case ARRAY:
                count = fieldMetaInfo.getSize();
                if(-1 != fieldMetaInfo.getMagicField().dynamicSizeFromOrder() && -1 == count) {
                    int order = fieldMetaInfo.getMagicField().dynamicSizeFromOrder() - 1;
                    count = convertToInt(object,  classMetaInfo.getFields().get(order));
                }

                Object array = Array.newInstance(fieldMetaInfo.getClazz(), count);
                for(int i=0, unitBytes = fieldMetaInfo.getTotalBytes() / fieldMetaInfo.getSize(); i<count; i++) {
                    TypeEnum typeEnum  = TypeEnum.getType(fieldMetaInfo.getClazz());
                    if(TypeEnum.OBJECT == typeEnum) {
                        bytes = new byte[unitBytes];
                        data.get(bytes);
                        if(classMetaInfo.isAutoTrim() && CalcUtil.isEmptyData(bytes)) continue;

                        Array.set(array, i, packObject(bytes, fieldMetaInfo.getClazz()));
                    } else {

                        Array.set(array, i, getBaseFieldValue(typeEnum, data));
                    }
                }

                CalcUtil.setValue(object, array, fieldMetaInfo.getField());
                break;
            case LIST:
                count = fieldMetaInfo.getSize();
                if(-1 != fieldMetaInfo.getMagicField().dynamicSizeFromOrder() && -1 == count) {
                    int order = fieldMetaInfo.getMagicField().dynamicSizeFromOrder() - 1;
                    count = convertToInt(object,  classMetaInfo.getFields().get(order));
                }

                List<Object> list = new ArrayList<>(count);
                for(int i=0, unitBytes = fieldMetaInfo.getTotalBytes() / fieldMetaInfo.getSize(); i<count; i++) {
                    TypeEnum typeEnum  = TypeEnum.getType(fieldMetaInfo.getClazz());
                    if(TypeEnum.OBJECT == typeEnum) {
                        bytes = new byte[unitBytes];
                        data.get(bytes);
                        if(classMetaInfo.isAutoTrim() && CalcUtil.isEmptyData(bytes)) continue;

                        list.add( packObject(bytes, fieldMetaInfo.getClazz()));
                    } else {
                        list.add(getBaseFieldValue(typeEnum, data));
                    }
                }

                CalcUtil.setValue(object, list, fieldMetaInfo.getField());
                break;
            case OBJECT:
                bytes = new byte[fieldMetaInfo.getTotalBytes()];
                data.get(bytes);
                CalcUtil.setValue(object, packObject(bytes, fieldMetaInfo.getClazz()), fieldMetaInfo.getField());
                break;
        }

    }


    public static Integer convertToInt(Object object, FieldMetaInfo field) {
        Object o =  CalcUtil.readValue(object, field.getField());

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
