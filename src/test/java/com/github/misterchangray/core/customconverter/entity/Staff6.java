package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@MagicClass
public class Staff6 {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2, calcLength = true)
    private int length;
    @MagicConverter(converter = CustomDateConverter.class, fixSize = 14)
    @MagicField(order = 3)
    private Date birthday;
    @MagicField(order = 14, size = 4)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class CustomDateConverter implements MConverter<Date> {
        SimpleDateFormat timestampFormatter =  new SimpleDateFormat("yyyyMMddHHmmss");

        @Override
        public MResult<Date> pack(int nextReadIndex, byte[] fullBytes, String attachParams) {
            byte[] tmp = Arrays.copyOfRange(fullBytes, nextReadIndex, nextReadIndex + 14);
            String s = new String(tmp);
            MResult build = null;
            try {
                build = MResult.build( timestampFormatter.parse(s));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return build;
        }

        @Override
        public byte[] unpack(Date object, String attachParams) {
            String format = timestampFormatter.format(object);
            return format.getBytes();
        }
    }

}
