package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.warpper.UByte;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.clazz.warpper.ULong;
import com.github.misterchangray.core.clazz.warpper.UShort;

import java.util.List;

@MagicClass()
public class ArrayOfUndnetNumber {
    @MagicField(order = 1, size = 3)
    private UInt[] ids;
    @MagicField(order = 3, size = 2)
    private UByte[] childIdss;
    @MagicField(order = 4, size = 3)
    private UShort[] parentIds;
    @MagicField(order = 6, size = 4)
    private List<ULong> phons;

    public UInt[] getIds() {
        return ids;
    }

    public void setIds(UInt[] ids) {
        this.ids = ids;
    }

    public UByte[] getChildIdss() {
        return childIdss;
    }

    public void setChildIdss(UByte[] childIdss) {
        this.childIdss = childIdss;
    }

    public UShort[] getParentIds() {
        return parentIds;
    }

    public void setParentIds(UShort[] parentIds) {
        this.parentIds = parentIds;
    }

    public List<ULong> getPhons() {
        return phons;
    }

    public void setPhons(List<ULong> phons) {
        this.phons = phons;
    }
}
