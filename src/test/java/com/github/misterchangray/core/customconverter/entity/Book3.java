package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.Date;

@MagicClass
public class Book3 {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2)
    private int code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
