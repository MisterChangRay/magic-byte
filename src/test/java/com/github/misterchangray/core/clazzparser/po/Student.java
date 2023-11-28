package com.github.misterchangray.core.clazzparser.po;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

@MagicClass
public class Student {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2, size = 10)
    private String name;
    @MagicField(order = 3, size = 1)
    private List<Book> books;

    @MagicField(order = 4)
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
