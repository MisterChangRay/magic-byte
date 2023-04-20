package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.customconverter.customconverter.CustomBookConverter;

@MagicClass
public class Staff10 {
    @MagicField(order = 1)
    private int id;
    @MagicConverter(converter = CustomBookConverter.class, attachParams = "3")
    @MagicField(order = 3)
    private Book2 book;
    @MagicField(order = 4, size = 4)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book2 getBook() {
        return book;
    }

    public void setBook(Book2 book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
