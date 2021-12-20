package com.github.misterchangray.core.enums;

import java.lang.reflect.Array;
import java.util.*;

public enum TypeEnum {
    BYTE(1, Byte.class, byte.class),
    BOOLEAN(1, Boolean.class, boolean.class),

    CHAR(2, Character.class, char.class),
    SHORT(2, Short.class, short.class),
    INT(4, Integer.class, int.class),
    LONG(8, Long.class, long.class),
    FLOAT(4, Float.class, float.class),
    DOUBLE(8, Double.class, double.class),

    STRING(1, String.class, null),

    ARRAY(-1, Array.class, null),
    LIST(-1, List.class, null),
    OBJECT(-1, Object.class, null),
    ;

    private int bytes;
    private Class<?> type;
    private Class<?> rawType;


    /**
     *
     * @param bytes
     * @param type  class of type
     * @param rawType  class of raw type, such as , int is raw type of Integer
     */
    TypeEnum(int bytes, Class<?> type, Class<?> rawType) {
        this.bytes = bytes;
        this.type = type;
        this.rawType = rawType;
    }

    public int getBytes() {
        return bytes;
    }


    public Class<?> getType() {
        return type;
    }

    public Class<?> getRawType() {
        return rawType;
    }
}
