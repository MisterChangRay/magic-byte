package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.customconverter.customconverter.CustomIntConverter;
import com.github.misterchangray.core.customconverter.customconverter.CustomIntConverter2;

/**
 * 测试自定义序列化
 *
 * 当配置到字段级别时，应该相互不影响
 */
@MagicClass
public class Staff14 {
    @MagicField(order = 1)
    private int id;
    @MagicConverter(converter = CustomIntConverter.class,  attachParams = {"1"})
    @MagicField(order = 3)
    private int boodId1;

    @MagicConverter(converter = CustomIntConverter2.class, attachParams = {"2"})
    @MagicField(order = 4)
    private int nameLen;
    @MagicField(order = 5, dynamicSizeOf = "nameLen")
    private String name;

    public int getBoodId1() {
        return boodId1;
    }

    public void setBoodId1(int boodId1) {
        this.boodId1 = boodId1;
    }

    public int getNameLen() {
        return nameLen;
    }

    public void setNameLen(int nameLen) {
        this.nameLen = nameLen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
