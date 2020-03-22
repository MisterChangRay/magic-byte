package com.github.misterchangray.core.enums;


import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.ClassUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TypeEnum {
    BYTE(1), BOOLEAN(1),
    SHORT(2), CHAR(2),
    INT(4),
    LONG(8),
    FLOAT(4),
    DOUBLE(8),
    STRING(0),
    ARRAY(0),
    LIST(0),
    OBJECT(0)
    ;


    /**
     * fast get class type
     */
    private static Map<Class, TypeEnum> FAST_MAPPING = new HashMap(20);
    static {
        FAST_MAPPING.put(byte.class, TypeEnum.BYTE);
        FAST_MAPPING.put(Byte.class, TypeEnum.BYTE);
        FAST_MAPPING.put(char.class, TypeEnum.CHAR);
        FAST_MAPPING.put(Character.TYPE, TypeEnum.CHAR);
        FAST_MAPPING.put(boolean.class, TypeEnum.BOOLEAN);
        FAST_MAPPING.put(Boolean.class, TypeEnum.BOOLEAN);
        FAST_MAPPING.put(short.class, TypeEnum.SHORT);
        FAST_MAPPING.put(Short.class, TypeEnum.SHORT);
        FAST_MAPPING.put(int.class, TypeEnum.INT);
        FAST_MAPPING.put(Integer.class, TypeEnum.INT);
        FAST_MAPPING.put(long.class, TypeEnum.LONG);
        FAST_MAPPING.put(Long.class, TypeEnum.LONG);

        FAST_MAPPING.put(float.class, TypeEnum.FLOAT);
        FAST_MAPPING.put(Float.class, TypeEnum.FLOAT);
        FAST_MAPPING.put(double.class, TypeEnum.DOUBLE);
        FAST_MAPPING.put(Double.class, TypeEnum.DOUBLE);
    };


    private int bytes;

    TypeEnum(int bytes) {
        this.bytes = bytes;
    }

    public int getBytes() {
        return bytes;
    }


    /**
     * get type of class
     * @param clazz
     * @return
     */
    public static TypeEnum getType(Class clazz) {
        AssertUtil.assertTypeNotNormalType(clazz);
        if(null != FAST_MAPPING.get(clazz)) return FAST_MAPPING.get(clazz);
        if(clazz.equals(String.class)) return TypeEnum.STRING;
        if(clazz.isArray()) return TypeEnum.ARRAY;
        if(ClassUtil.isSubClass(clazz, List.class)) return TypeEnum.LIST;
        return TypeEnum.OBJECT;

    }
}
