package com.github.misterchangray.core.enums;

import com.github.misterchangray.core.clazz.warpper.*;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 所有支持的数据类型枚举
 */
public enum TypeEnum {
    BYTE(1, Byte.class, byte.class),
    BOOLEAN(1, Boolean.class, boolean.class),

    CHAR(2, Character.class, char.class),
    SHORT(2, Short.class, short.class),
    INT(4, Integer.class, int.class),
    LONG(8, Long.class, long.class),
    FLOAT(4, Float.class, float.class),
    DOUBLE(8, Double.class, double.class),

    STRING(1, String.class),

    DATETIME(6, Date.class, Instant.class, LocalTime.class, LocalDate.class, LocalDateTime.class),

    UBYTE(1, UByte.class),
    USHORT(2, UShort.class),
    UINT(4, UInt.class),
    ULONG(8, ULong.class),
    UNUMBER(1, UNumber.class),

    ARRAY(-1, Array.class),
    LIST(-1, List.class),
    OBJECT(-1, Object.class),

    CUSTOM(-1, TypeEnum.class)
    ;

    private int bytes;
    private List<Class<?>> types;


    /**
     *
     * @param bytes
     * @param types  class of types
     */
    TypeEnum(int bytes, Class<?> ...types) {
        this.bytes = bytes;
        this.types = new ArrayList<>();
        for (Class<?> type : types) {
            this.types.add(type);
        }
    }

    public int getBytes() {
        return bytes;
    }


    public List<Class<?>> getTypes() {
        return types;
    }

    /**
     * checker type
     * @param args
     * @return
     */
    public boolean is(TypeEnum... args) {
        for (TypeEnum typeEnum : args) {
            if(this == typeEnum){
                return true;
            }
        }
        return false;
    }

}
