package com.github.misterchangray.core.entity.error;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 17:28
 **/
public class TestListString {
   @MagicField(order = 1, size = 2)
   private List<String> a;

    public List<String> getA() {
        return a;
    }

    public void setA(List<String> a) {
        this.a = a;
    }
}
