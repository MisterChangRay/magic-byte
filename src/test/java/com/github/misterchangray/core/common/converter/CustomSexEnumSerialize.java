package com.github.misterchangray.core.common.converter;

import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.common.converter.entity.SexEnum;
import com.github.misterchangray.core.intf.MConverter;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class CustomSexEnumSerialize implements MConverter<SexEnum> {

    @Override
    public MResult<SexEnum> pack(int nextReadIndex, byte[] fullBytes, String attachParams) {
        byte fullByte = fullBytes[4];
        SexEnum sexEnum = SexEnum.MAN;
        switch (fullByte) {
            case 1:
                sexEnum = SexEnum.MAN;
                break;
            case 2:
                sexEnum = SexEnum.WOMAN;
                break;
            case 3:
                sexEnum = SexEnum.UNKNOWN;
                break;
        }

        return MResult.build(1, sexEnum);
    }

    @Override
    public byte[] unpack(SexEnum sexEnum, String attachParams) {
        switch (sexEnum) {
            case MAN:
                return new byte[]{1};
            case WOMAN:
                return new byte[]{2};
            case UNKNOWN:
                return new byte[]{3};
        }
        return new byte[]{3};
    }
}