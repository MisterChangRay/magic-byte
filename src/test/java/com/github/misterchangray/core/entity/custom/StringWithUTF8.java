package com.github.misterchangray.core.entity.custom;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass()
public class StringWithUTF8 {
    @MagicField(order = 1, size = 30, charset = "UTF8")
   private String name;
    @MagicField(order = 2, size = 50)
   private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
