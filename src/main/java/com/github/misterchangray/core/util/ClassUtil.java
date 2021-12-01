package com.github.misterchangray.core.util;

import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import java.lang.reflect.Field;
import java.util.Objects;

public class ClassUtil {


    /**
     * 如果数据区全是由 0x00 和 0xFF 组成则认为是空数据,不具备解析价值
     * @param bytes
     * @return
     */
    public static boolean isEmptyData(byte[] bytes) {
        boolean res = true;
        for(byte b : bytes) {
            if(false == (b == 0xff || b == 0x00)) {
                res = false;
                break;
            }
        }
        return res;
    }


    public static Object readValue(Object o, Field field) {
        try {
            field.setAccessible(true);
            return  field.get(o);
        } catch (IllegalAccessException e) {
            throw new MagicByteException("can't read object value, please check setter and getter method ");
        }
    }



    public static void setValue(Object object, Object value, Field field) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new MagicByteException("can't set object value, please check setter and getter method ");
        }
    }


    public static void autoSetInt(Object object, Integer value, FieldMetaInfo field) {
        switch (field.getType()) {
            case BYTE:
                setValue(object, value.byteValue(), field.getField());
                break;
            case SHORT:
                setValue(object, value.shortValue(), field.getField());
                break;
            case INT:
                setValue(object, value, field.getField());
                break;
        }
    }


    public static <T> int readAsInt(FieldMetaInfo field, T o) {
        Object i =  readValue(o, field.getField());
        if(Objects.isNull(i)) {
            return 0;
        }
        switch (field.getType()) {
            case BYTE:
                return ((Byte) i).intValue();
            case SHORT:
                return ((Short) i).intValue();
            case INT:
                return (Integer) i;
        }
        return 0;
    }
}
