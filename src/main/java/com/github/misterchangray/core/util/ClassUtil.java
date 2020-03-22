package com.github.misterchangray.core.util;

import com.github.misterchangray.core.exception.MagicByteException;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ClassUtil {



    /**
     * 判断类是否有继承关系
     * @param child
     * @param parent
     * @return
     */
    public static boolean isSubClass(Class child, Class parent) {
        try {
            child.asSubclass(parent);
            return true;
        } catch (ClassCastException ae) { }
        return false;
    }


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

}
