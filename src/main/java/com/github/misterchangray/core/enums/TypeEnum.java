package com.github.misterchangray.core.enums;


import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.ClassUtil;

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

    STRING(-1, String.class, null),
    ARRAY(-1, Array.class, null),
    LIST(-1, List.class, null),
    OBJECT(-1, Object.class, null),
    ;


    /**
     * fast get class type
     */
    public static Map<Class<?>, TypeEnum> SUPPORTED_TYPES = new HashMap<Class<?>, TypeEnum>(20);
    static {
        for (TypeEnum value : TypeEnum.values()) {
            SUPPORTED_TYPES.put(value.type, value);
            if(Objects.nonNull(value.rawType)) {
                SUPPORTED_TYPES.put(value.rawType, value);
            }
        }
    };


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


    /**
     * get type of class
     * @param clazz
     * @return
     */
    public static TypeEnum getType(Class<?> clazz) {
        TypeEnum res = null;
        res = SUPPORTED_TYPES.get(clazz);
        if(Objects.nonNull(res)) {
            return res;
        } else if(clazz.isArray()) {
            res = TypeEnum.ARRAY;
        } else if(clazz.isAssignableFrom(List.class)) {
            res = TypeEnum.LIST;
        } else {
            res = TypeEnum.OBJECT;
        }

        return res;
    }

}
