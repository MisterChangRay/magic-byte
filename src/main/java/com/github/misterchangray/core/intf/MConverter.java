package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.MResult;

/**
 * @param <T>
 * @description: 此类用于实现自己的序列化逻辑
 * @author: Ray.chang
 * @create: 2023-02-08 15:11
 */
public interface MConverter<T> {

    /**
     * 将字节数据打包为对象
     * 注意这里是全部的字节数据
     *
     * @param nextReadIndex 起始位置，即在此之前的都已读取
     * @param fullBytes     完整字节数据
     * @param attachParams  附加参数
     * @param clz           打包对象所属类
     * @param fieldObj      当前处理的对象
     * @param rootObj       处理的根基对象
     * @return 反序列化用的数据长度和对象
     */
    MResult<T> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<T> clz, Object fieldObj, Object rootObj);

    /**
     * 将对象拆包为字节
     * <p>
     * magicByte 会将返回的字节数据整合到全部数据集中
     *
     * @param object       泛型对应类的实例
     * @param attachParams 附加参数
     * @param rootObj 处理的根基对象
     * @return 序列化的二进制数据
     */
    default byte[] unpack(T object, String[] attachParams, Object rootObj) {
        return MagicByte.unpackToByte(object);
    }
}
