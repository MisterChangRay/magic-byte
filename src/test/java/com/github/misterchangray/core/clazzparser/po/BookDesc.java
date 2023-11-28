package com.github.misterchangray.core.clazzparser.po;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class BookDesc {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2, size = 10)
    private String name;

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
