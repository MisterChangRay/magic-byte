package com.github.misterchangray.core.common.converter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.common.converter.entity.SexEnum;
import com.github.misterchangray.core.intf.MConverter;


/**
 * 自定义一个枚举转换器
 */
public class CustomSexEnumSerialize implements MConverter<SexEnum> {
    // 定义为数组, 固定枚举顺序
    private SexEnum[] sexEnums = new SexEnum[] {SexEnum.MAN, SexEnum.WOMAN, SexEnum.UNKNOWN};

    /**
     * 封包，将字节数据转为对象
     * @param nextReadIndex 起始位置，即在此之前的都已读取
     * @param fullBytes 完整字节数据
     * @param attachParams 附加参数
     * @return 返回总共读取的字节数和封装好的对象
     */
    @Override
    public MResult<SexEnum> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class<SexEnum> clz, Object sexEnum, Object root) {
        byte index = fullBytes[4];

        // 注意，如果没有在注解中申明`fixSize`, 这里必须要返回读取的字节数
        return MResult.build(
                1,
                sexEnums[index]
        );
    }

    /**
     * 拆包，将对象拆包为字节数组
     * @param sexEnum
     * @param attachParams 附加参数
     * @return
     */
    @Override
    public byte[] unpack(SexEnum sexEnum, String[] attachParams, Object rootObj) {
        for (int i = 0; i < sexEnums.length; i++) {
            if(sexEnum == sexEnums[i]) {
                return new byte[]{(byte)i};
            }
        }
        return null;
    }
}