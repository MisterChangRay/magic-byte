package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.*;
import com.github.misterchangray.core.enums.ByteOrder;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.intf.MagicMessage;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class MagicByte {
    private static UnPacker unPacker = UnPacker.getInstance();
    private static Packer packer = Packer.getInstance();
    private static MagicChecker magicChecker;

    /**
     * 返回结构体总字节数
     * 注意：如果结构体有动态大小，那么返回值为最小字节数
     * @return
     */
    public static int structBytes(Class<?> magicClazz) {
        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(magicClazz);
        return classMetaInfo.getElementBytes();
    }


    /**
     * 尝试使用命令委托进行解析,解析失败则返回null
     * @param data
     * @return
     * @param <T>
     * @throws MagicByteException
     */
    public static <T extends MagicMessage> T pack(byte[] data) throws MagicByteException {
        if(!MessageManager.hasMessage()) {
            return null;
        }
        return pack(data, null, magicChecker);
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
        MResult<T> mResult = doPack(data, clazz, checker);
        return mResult.getData();
    }


    /**
     * 尝试使用命令委托进行解析
     * 解析失败则返回null
     * @param data
     * @return MResult, 包含解析的字节数和对象
     * @param <T>
     * @throws MagicByteException
     */
    public static  <T extends MagicMessage> MResult<T>  packExt(byte[] data) throws MagicByteException {
        if(!MessageManager.hasMessage()) {
            return null;
        }
        return packExt(data, null, magicChecker);
    }



    /**
     *
     * 将字节数组按照Class的定义封装成对象
     * 本方法不对字节进行效验; 如果字节缺失也可进行转换, 但是转换后的对象将会不完整
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return MResult, 包含解析的字节数和对象
     */
    public static <T> MResult<T> packExt(byte[] data, Class<?> clazz) throws MagicByteException {
        return packExt(data, clazz, magicChecker);
    }

    /**
     *
     * 将字节数组按照Class的定义封装成对象
     *
     * @param data
     * @param clazz
     * @param <T>
     * @param checker
     * @return  MResult, 包含解析的字节数和对象
     */
    public static <T> MResult<T> packExt(byte[] data, Class<?> clazz, MagicChecker checker) throws MagicByteException {
        return doPack(data, clazz, checker);
    }


    /**
     * 执行 pack 操作
     * @param data
     * @param clazz
     * @param checker
     * @return
     * @param <T>
     */
    private static <T> MResult<T> doPack(byte[] data, Class<?> clazz, MagicChecker checker) {
        if(null == data || 0 == data.length) return null;

        DynamicByteBuffer res = DynamicByteBuffer.allocate(data.length);
        res.put(data);
        res.position(0);

        Object o = packer.packObject(res, clazz, checker);
        return MResult.build(res.position(), o);
    }

    /**
     *
     * 将对象封装成字节Buffer, 方便后续操作
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ByteBuffer unpack(T data) throws MagicByteException {
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
    public static <T> ByteBuffer unpack(T data, MagicChecker checker) throws MagicByteException {
        if (null == data) return null;
        DynamicByteBuffer res = unPacker.unpackObject(data, checker);
        res.flip();
        return res.buffer();
    }

    /**
     * 注册消息
     *
     * 普通的消息进行`pack`操作时需要传入两个参数, 字节数据和类结构.
     *
     * 消息进行注册后，调用`pack`则只需传一个参数：字节数据。
     * 框架将会根据类定义的命令字节自动查询注册的消息进行解码操作。
     *
     * @param msgClazz 消息类,必须实现 MagicMessage 接口
     */
    public static void registerCMD(Class<? extends  MagicMessage> msgClazz) {
        MessageManager.register(msgClazz);
    }

    /**
     * 注册指定命令的消息
     *
     * 此方式提供显示的命令注册，即将参数1 的 cmd 命令和 类进行绑定，以供`pack`操作时使用。
     *
     * @param cmd  命令值
     * @param msgClazz 消息类,必须实现 MagicMessage 接口
     */
    public static void registerCMD(int cmd, Class<? extends  MagicMessage> msgClazz) {
        MessageManager.register(cmd, msgClazz);
    }


    /**
     * 配置全局的校验和操作
     * @param checker
     */
    public static void configMagicChecker(MagicChecker checker) {
        magicChecker = checker;
    }


    /**
     * 全局配置默认字符集
     */
    public static void configDefaultCharset(Charset charset) {
        GlobalConfigs.setGlobalDefaultCharset(charset);
    }

    /**
     * 设置全局默认端序
     * <p>注：如果传入的类型是 AUTO 则自动转为 BIG_ENDIAN</p>
     *
     * @param order 端序
     */
    public static void configDefaultByteOrder(ByteOrder order) {
        GlobalConfigs.setGlobalDefaultByteOrder(order);
    }

}
