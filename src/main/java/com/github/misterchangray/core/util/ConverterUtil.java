package com.github.misterchangray.core.util;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicByteException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class ConverterUtil {
    public static BigInteger BYTE = new BigInteger("F", 16);
    public static BigInteger WORD = new BigInteger("FF", 16);
    public static BigInteger DWORD = new BigInteger("FFFF", 16);
    public static BigInteger QWORD = new BigInteger("FFFFFFFF", 16);


    /**
     * 将正整数转换为字节数组
     *
     * 只支持正整数
     * @param p
     * @return
     */
    public static byte[] numberToByte(long p, int length) {
        byte[] res = new byte[length];
        for (int i = length - 1; i >= 0 && p>=0 ; i--) {
            res[i] = (byte)(p & 0xff);
            p = p >>8 ;
        }

        return res;
    }

    /**
     * 数组转为正整数
     *
     * 只支持正整数
     * @param p
     * @return
     */
    public static long byteToNumber(byte[] p) {
        long res = 0;
        for (byte b : p) {
            res <<= 8;
            res |= (b & 0xff);
        }
        return res;
    }

    /**
     * 16进制转换为字节;这里16进制是无符号的
     * @param aint
     * @return
     */
    public static String intToHexString(int aint) {
        if(aint < 0) {
            throw new MagicByteException("Cannot convert negative");
        }
        return Integer.toString(aint, 16);
    }


    /**
     * byte转换为无符号数
     * @param abyte
     * @return
     */
    public static int byteToUnsigned(byte abyte) {
        return 0xff & abyte;
    }

    /**
     * 16进制转换为字节;这里16进制是无符号的
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexStringToByte(String hexStr) {
        if(null == hexStr) return null;

        hexStr = hexStr.replaceAll("^0[Xx]", "");
        int len = hexStr.length();
        if(len % 2 != 0){
            hexStr = "0" + hexStr;
            len ++;
        }

        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexStr.charAt(i), 16) << 4)
                    + Character.digit(hexStr.charAt(i+1), 16));
        }
        return data;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * 字节数组转未16进制字符串
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        if(null == bytes) return null;

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }


    /**
     * 对象转为具体的值
     * @param typeEnum
     * @param o
     * @return
     */
    public static long toNumber(TypeEnum typeEnum, Object o) {
        if(Objects.isNull(o)) return 0;

        switch (typeEnum) {
            case BYTE:
                return (byte) o;
            case SHORT:
                return (short) o;
            case INT:
                return (int) o;
            case LONG:
                return (long) o;
        }
        return 0;
    }




    /**
     * 对象转为具体的值
     * @param typeEnum
     * @param o
     * @return
     */
    public static long toNumber(TypeEnum typeEnum, long o) {
        if(Objects.isNull(o)) return 0;

        switch (typeEnum) {
            case BYTE:
                return (byte) o;
            case SHORT:
                return (short) o;
            case INT:
                return (int) o;
            case LONG:
                return (long) o;
        }
        return 0;
    }

    /**
     * 对象转为具体的值
     * @param typeEnum
     * @param val
     * @return
     */
    public static Object toTargetObject(TypeEnum typeEnum, long val) {
        if(Objects.isNull(val)) return 0;

        switch (typeEnum) {
            case BYTE:
                return (byte) val;
            case SHORT:
                return (short) val;
            case INT:
                return (int) val;
            case LONG:
                return (long) val;
        }
        return 0;
    }
}
