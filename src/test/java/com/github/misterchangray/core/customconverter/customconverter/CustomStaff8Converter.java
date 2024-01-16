package com.github.misterchangray.core.customconverter.customconverter;


import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.customconverter.entity.Staff7;
import com.github.misterchangray.core.customconverter.entity.Staff8;
import com.github.misterchangray.core.intf.MConverter;

public  class CustomStaff8Converter implements MConverter<Staff8> {

    @Override
    public MResult<Staff8> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class clz, Object tmp, Object root) {
        Staff8 staff8 = new Staff8();
        staff8.setId(fullBytes[0]);
        staff8.setLength(fullBytes[1]);
        return MResult.build(staff8).setBytes(2);
    }

    @Override
    public byte[] unpack(Staff8 object, String[] attachParams, Object rootObj) {
        return new byte[]{(byte) object.getId(), (byte) object.getLength()};
    }
}