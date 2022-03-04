package com.github.misterchangray.core.common.entity.error;

import com.github.misterchangray.core.annotation.MagicField;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-17 17:28
 **/
public class TestArrayMatrix {
    @MagicField(order = 1, size = 1)
    private int[] b;

    @MagicField(order = 2, size = 1)
    private int[][] a;

    public TestArrayMatrix(int[] b, int[][] a) {
        this.b = b;
        this.a = a;
    }
}
