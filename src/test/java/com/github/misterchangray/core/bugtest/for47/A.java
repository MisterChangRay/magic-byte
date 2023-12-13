package com.github.misterchangray.core.bugtest.for47;


import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.Objects;

@MagicClass
public class A {

    @MagicField(order = 1)
    private int id;

    @MagicField(order = 2)
    @MagicConverter(converter = BConvert.class)
    private String b;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        A a = (A) o;
        return id == a.id && Objects.equals(b, a.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, b);
    }


}