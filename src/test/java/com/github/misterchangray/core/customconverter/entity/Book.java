package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.Date;

@MagicClass
public class Book {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2)
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
