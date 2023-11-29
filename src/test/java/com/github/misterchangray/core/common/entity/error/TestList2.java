package com.github.misterchangray.core.common.entity.error;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 17:28
 **/
public class TestList2 {
   @MagicField(order = 1, size = 1, dynamicSizeOf = "3")
   private List<Integer> a;

    public List<Integer> getA() {
        return a;
    }

    public void setA(List<Integer> a) {
        this.a = a;
    }
}
