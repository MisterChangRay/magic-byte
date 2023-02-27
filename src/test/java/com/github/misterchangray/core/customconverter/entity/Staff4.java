package com.github.misterchangray.core.customconverter.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.customconverter.CustomBook3Converter;

import java.util.List;

@MagicClass
public class Staff4 {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2, calcLength = true)
    private int length;
    @MagicConverter(converter = CustomBook3Converter.class, attachParams = "1")
    @MagicField(order = 5, size = 2)
    private List<Book3> book;
    @MagicConverter(converter = CustomBook3Converter.class, attachParams = "2")
    @MagicField(order = 7, size = 2)
    private List<Book3> book2;
    @MagicField(order = 14, size = 4)
    private String name;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Book3> getBook() {
        return book;
    }

    public void setBook(List<Book3> book) {
        this.book = book;
    }

    public List<Book3> getBook2() {
        return book2;
    }

    public void setBook2(List<Book3> book2) {
        this.book2 = book2;
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
