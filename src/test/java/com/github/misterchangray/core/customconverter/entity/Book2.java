package com.github.misterchangray.core.customconverter.entity;

import java.util.Date;

public class Book2 implements IBook {
    private int id;
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
