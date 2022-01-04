package com.github.misterchangray.core.util;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicByteException;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class ConverterUtil {
    public static BigInteger BYTE = new BigInteger("F", 16);
    public static BigInteger WORD = new BigInteger("FF", 16);
    public static BigInteger DWORD = new BigInteger("FFFF", 16);
    public static BigInteger QWORD = new BigInteger("FFFFFFFF", 16);


    /**
     * 16进制转换为字节;这里16进制是无符号的
     * @param aint
     * @return
     */
    public static String intToHexString(int aint) {
        if(aint < 0) {
            throw new MagicByteException("Cannot convert negative");
        }
        BigInteger bigInteger = new BigInteger(String.valueOf(aint));
        return bigInteger.toString(16);
    }


    /**
     * 整数转换为byte数组, 只转换无符号整数
     * @param aint
     * @return
     */
    public static byte[] intToByte(int aint) {
        if(aint < 0) {
            throw new MagicByteException("Cannot convert negative");
        }

        BigInteger bigInteger = new BigInteger(String.valueOf(aint));
        byte[] res = bigInteger.toByteArray();
        return Arrays.copyOfRange(res, 1, res.length);
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
        BigInteger bigInteger = new BigInteger(hexStr, 16);
        byte[] res = bigInteger.toByteArray();
        return Arrays.copyOfRange(res, 1, res.length);
    }

    /**
     * 字节数组转未16进制字符串
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        if(null == bytes) return null;

        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + 1);
        byteBuffer.put((byte) 0);
        byteBuffer.put(bytes);
        BigInteger bigInteger = new BigInteger(byteBuffer.array());
        return bigInteger.toString(16);
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


}
