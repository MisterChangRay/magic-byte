package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.clazz.MResult;

/**
 *
 * @description: 此类用于实现自己的序列化逻辑
 * @author: Ray.chang
 * @create: 2023-02-08 15:11
 * @param <T>
 */
public interface MConverter<T> {

    /**
     *
     * 将字节数据打包为对象
     * 注意这里是全部的字节数据
     * @param nextReadIndex 起始位置，即在此之前的都已读取
     * @param fullBytes 完整字节数据
     * @param attachParams 附加参数
     * @return
     */
    default MResult<T> pack(int nextReadIndex, byte[] fullBytes, String attachParams) {
        return MResult.build(0, null);
    }

    /**
     * 将对象拆包为字节
     *
     * magicByte 会将返回的字节数据整合到全部数据集中
     * @param object
     * @param attachParams 附加参数
     * @return
     * @throws IllegalAccessException
     */
    default byte[] unpack(T object, String attachParams) {
        return null;
    };
}
