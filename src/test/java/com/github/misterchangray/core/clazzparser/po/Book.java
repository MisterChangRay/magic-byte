package com.github.misterchangray.core.clazzparser.po;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class Book {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2, size = 10)
    private String name;
    @MagicField(order = 3)
    private BookDesc bookDesc;

    public BookDesc getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(BookDesc bookDesc) {
        this.bookDesc = bookDesc;
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
