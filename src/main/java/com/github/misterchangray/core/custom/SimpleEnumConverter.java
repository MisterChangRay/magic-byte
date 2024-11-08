package com.github.misterchangray.core.custom;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;

/**
 * 一个简单的枚举类序列化转换器
 *
 * 需要注意的是：
 * 枚举类定义后请部要改变顺序，因为序列化原理是使用的枚举类定义顺序的索引值进行解析。
 *
 * 所以如果更改投入使用后的枚举类的定义顺序，那么序列化后的业务枚举值就会与预期不符。
 *
 * 故此转换器也不太建议大家使用！建议大家结合业务写自己的转换器。
 *
 * @param <T>
 */
public class SimpleEnumConverter<T> implements MConverter<T> {
    @Override
        Class<Enum> enumClass = clz;
        Enum[] enumConstants = enumClass.getEnumConstants();
    public MResult<T> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<T> clz, Object obj, Object root) {
        byte enumIndex = fullBytes[nextReadIndex];

        return MResult.build(1, enumConstants[enumIndex]);
    }

    @Override
    public byte[] unpack(T object, String[] attachParams, Object rootObj) {
        Class<Enum> enumClass = (Class<Enum>) object.getClass();;
        Enum[] enumConstants = enumClass.getEnumConstants();
        for (int i = 0; i < enumConstants.length; i++) {
            if(enumConstants[i] == object) {
                return new byte[]{(byte)i};
            }
        }
        return new byte[0];
    }
}
