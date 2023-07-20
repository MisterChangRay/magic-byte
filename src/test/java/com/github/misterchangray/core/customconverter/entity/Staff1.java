package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.customconverter.customconverter.CustomBookConverter;

@MagicClass
public class Staff1 {
    @MagicField(order = 1)
    private int id;
    @MagicConverter(converter = CustomBookConverter.class, attachParams = {"1"})
    @MagicField(order = 2)
    private Book book;
    @MagicConverter(converter = CustomBookConverter.class, attachParams = {"2"})
    @MagicField(order = 3)
    private Book book2;
    @MagicField(order = 4, size = 4)
    private String name;

    public Book getBook2() {
        return book2;
    }

    public void setBook2(Book book2) {
        this.book2 = book2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
