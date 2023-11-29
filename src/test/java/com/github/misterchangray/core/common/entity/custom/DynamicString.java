package com.github.misterchangray.core.common.entity.custom;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass()
public class DynamicString {
    @MagicField(order = 1)
   private int len;
    @MagicField(order = 2, dynamicSizeOf = "len")
   private String email;

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
}
