package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.customconverter.customconverter.CustomConverterDynamicSize;

import java.util.List;

@MagicClass
public class ConverterWithDynamicSize {
    @MagicField(order = 1)
    private int id;
    @MagicConverter(converter = CustomConverterDynamicSize.class)
    @MagicField(order = 2, dynamicSize = true, size = 10)
    private List<Book3> name;
    @MagicField(order = 3)
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book3> getName() {
        return name;
    }

    public void setName(List<Book3> name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
