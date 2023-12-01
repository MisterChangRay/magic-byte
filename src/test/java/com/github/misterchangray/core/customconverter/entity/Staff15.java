package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UNumber;
import com.github.misterchangray.core.customconverter.customconverter.CustomIntConverter;

/**
 * 测试自定义序列化
 *
 * 当配置到字段级别时，应该相互不影响
 */
@MagicClass
public class Staff15 {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 3, size = 3)
    private UNumber boodId1;

    @MagicConverter(converter = CustomIntConverter.class,  attachParams = {"1"})
    @MagicField(order = 4, size = 2)
    private UNumber nameLen;
    @MagicField(order = 5, dynamicSizeOf = "nameLen")
    private String name;

    public UNumber getBoodId1() {
        return boodId1;
    }

    public void setBoodId1(UNumber boodId1) {
        this.boodId1 = boodId1;
    }

    public UNumber getNameLen() {
        return nameLen;
    }

    public void setNameLen(UNumber nameLen) {
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
