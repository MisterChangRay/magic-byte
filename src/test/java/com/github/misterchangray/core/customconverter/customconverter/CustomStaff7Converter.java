package com.github.misterchangray.core.customconverter.customconverter;


import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.customconverter.entity.Staff7;
import com.github.misterchangray.core.intf.MConverter;

public  class CustomStaff7Converter implements MConverter<Staff7> {

    @Override
    public MResult<Staff7> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class clz, Object obj) {
        Staff7 staff7 = new Staff7();
        staff7.setId(fullBytes[0]);
        staff7.setLength(fullBytes[1]);
        return MResult.build(staff7);
    }

    @Override
    public byte[] unpack(Staff7 object, String attachParams[]) {
        return new byte[]{(byte) object.getId(), (byte) object.getLength()};
    }
}