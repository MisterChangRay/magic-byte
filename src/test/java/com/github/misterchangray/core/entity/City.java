package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

@MagicClass
public class City {
    @MagicField(order = 1, size = 10, autoTrim = true, charset = "UTF8")
    private String name;

    @MagicField(order = 2)
    private boolean has;

    @MagicField(order = 3)
    private int highSchoolSize;

    @MagicField(order = 4, dynamicSizeOf = 3, autoTrim = true)
    private List<HighSchool> highSchoolList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

    public int getHighSchoolSize() {
        return highSchoolSize;
    }

    public void setHighSchoolSize(int highSchoolSize) {
        this.highSchoolSize = highSchoolSize;
    }

    public List<HighSchool> getHighSchoolList() {
        return highSchoolList;
    }

    public void setHighSchoolList(List<HighSchool> highSchoolList) {
        this.highSchoolList = highSchoolList;
    }
}
