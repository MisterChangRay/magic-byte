package com.github.misterchangray.core;

import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.util.PackUtil;
import com.github.misterchangray.core.util.UnpackUtil;

import java.nio.ByteBuffer;


public class MagicByte {

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
        if(null == data || null == clazz) return null;
        if(0 == data.length) return null;

        return PackUtil.packObject(data, clazz);
    }

    /**
     *
     * 将对象封装成字节Buffer, 方便后续操作
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ByteBuffer unpack(T t)  throws MagicByteException {
        if (null == t) return null;

        return UnpackUtil.unpackObject(t);
    }


    /**
     *  将对象转为字节数组
     * @param t
     * @param <T>
     * @return
     */
    public static <T> byte[] unpackToByte(T t) throws MagicByteException {
        if (null == t) return null;

        ByteBuffer res = unpack(t);
        if(null == res) return null;
        return res.array();
    }
}
