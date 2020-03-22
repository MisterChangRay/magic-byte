package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass(autoTrim = true)
public class Phone {
    @MagicField(order = 1, size = 8)
    private String brand;
    @MagicField(order = 2)
    private long phone;


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
