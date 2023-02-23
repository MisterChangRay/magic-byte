package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.TypeManager;
import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.intf.MConverter;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;


public class MagicByte {
    private static UnPacker unPacker = UnPacker.getInstance();
    private static Packer packer = Packer.getInstance();
    private static MagicChecker magicChecker;
    private static Charset defaultCharset = Charset.forName("ASCII");

    /**
     * 全局配置默认字符集
     */
    public static void configDefaultCharset(Charset charset) {
        defaultCharset = charset;
    }




    /**
     *
     * 将字节数组按照Class的定义封装成对象
     * 本方法不对字节进行效验; 如果字节缺失也可进行转换, 但是转换后的对象将会不完整
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T pack(byte[] data, Class<?> clazz) throws MagicByteException {
        return pack(data, clazz, magicChecker);
    }


    /**
     *
     * 将字节数组按照Class的定义封装成对象
     *
     * @param data
     * @param clazz
     * @param <T>
     * @param checker
     * @return
     */
    public static <T> T pack(byte[] data, Class<?> clazz, MagicChecker checker) throws MagicByteException {
        if(null == data || null == clazz) return null;
        if(0 == data.length) return null;

        DynamicByteBuffer res = DynamicByteBuffer.allocate(data.length);
        res.put(data);
        res.position(0);

        return packer.packObject(res, clazz, checker);
    }

    /**
     *
     * 将对象封装成字节Buffer, 方便后续操作
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ByteBuffer unpack(T data)  throws MagicByteException {
        if (null == data) return null;

        return unpack(data, magicChecker);
    }


    /**
     *  将对象转为字节数组
     * @param data
     * @param <T>
     * @return
     */
    public static <T> byte[] unpackToByte(T data) throws MagicByteException {
        if (null == data) return null;

        return  unpack(data, null).array();
    }

    /**
     *  将对象转为字节数组
     * @param data
     * @param <T>
     * @return
     */
    public static <T> byte[] unpackToByte(T data, MagicChecker checker) throws MagicByteException {
        if (null == data) return null;

        return  unpack(data, checker).array();
    }

    /**
     * 携带检查器, 可以计算校验和计算校验和
     * @param data
     * @param checker
     * @param <T>
     * @return
     * @throws MagicByteException
     */
    public static <T> ByteBuffer unpack(T data, MagicChecker checker)  throws MagicByteException {
        if (null == data) return null;
        DynamicByteBuffer res = unPacker.unpackObject(data, checker);
        return res.buffer();
    }


    public static void configMagicChecker(MagicChecker checker) {
        magicChecker = checker;
    }

}
