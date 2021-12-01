package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class DynamicString {
    @MagicField(order = 1)
    private int len;
    @MagicField(order = 2, dynamicSizeOf = 1)
    private String email;
    @MagicField(order = 3, size = 10, autoTrim = true)
    private String name;
    @MagicField(order = 4)
    private long date;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
