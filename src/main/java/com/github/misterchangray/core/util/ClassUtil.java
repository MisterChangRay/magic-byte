package com.github.misterchangray.core.util;

import com.github.misterchangray.core.exception.MagicByteException;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class CalcUtil {
    public static BigInteger BYTE = new BigInteger("F", 16);
    public static BigInteger WORD = new BigInteger("FF", 16);
    public static BigInteger DWORD = new BigInteger("FFFF", 16);
    public static BigInteger QWORD = new BigInteger("FFFFFFFF", 16);


    /**
     * 16进制转换为字节;这里16进制是无符号的
     * @param aint
     * @return
     */
    public static byte[] intToHexString(int aint) {
       if(aint < 0) {
            throw new MagicByteException("Cannot convert negative");
       }

        BigInteger bigInteger = new BigInteger(String.valueOf(aint));
        byte[] res = bigInteger.toByteArray();
        return Arrays.copyOfRange(res, 1, res.length);
    }

    /**
     * 16进制转换为字节;这里16进制是无符号的
     * @param hexStr
     * @return
     */
    public static byte[] hexStringToByte(String hexStr) {
        hexStr = hexStr.replaceAll("^0[Xx]", "");
        BigInteger bigInteger = new BigInteger(hexStr, 16);
        byte[] res = bigInteger.toByteArray();
        return Arrays.copyOfRange(res, 1, res.length);
    }

    /**
     * 字节数组转未16进制字符串
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + 1);
        byteBuffer.put((byte) 0);
        byteBuffer.put(bytes);
        BigInteger bigInteger = new BigInteger(byteBuffer.array());
        return bigInteger.toString(16);
    }


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
