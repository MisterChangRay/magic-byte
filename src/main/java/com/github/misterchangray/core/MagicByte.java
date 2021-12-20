package com.github.misterchangray.core;

import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.util.DynamicByteBuffer;
import java.nio.ByteBuffer;


public class MagicByte {
    private static UnPacker unPacker = UnPacker.getInstance();

    private static Packer packer = Packer.getInstance();

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

        DynamicByteBuffer res = DynamicByteBuffer.allocate(data.length);
        res.put(data);
        res.position(0);

        return packer.packObject(res, clazz);
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
        byte[] bytes = unpackToByte(t);
        return ByteBuffer.allocate(bytes.length).put(bytes);
    }


    /**
     *  将对象转为字节数组
     * @param t
     * @param <T>
     * @return
     */
    public static <T> byte[] unpackToByte(T t) throws MagicByteException {
        if (null == t) return null;

        return  unPacker.unpackObject(t);
    }
}
