package com.github.misterchangray.core.complex;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class NestingObject {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 3, size = 10)
    private String name;
    @MagicField(order = 5)
    private Level2 level2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level2 getLevel2() {
        return level2;
    }

    public void setLevel2(Level2 level2) {
        this.level2 = level2;
    }


    public static class Level2 {
        @MagicField(order = 1)
        private byte id;
        @MagicField(order = 3, size = 10)
        private String name;

        public byte getId() {
            return id;
        }

        public void setId(byte id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}


