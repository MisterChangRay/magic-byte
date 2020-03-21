package com.github.misterchangray.core.metainfo;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;

import java.lang.reflect.Field;

public class FieldMetaInfo {
    private MagicField magicField;
    private Field field;
    private int size;
    private int totalBytes;
    private TypeEnum type;
    private Class clazz;
    private String charset;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(int totalBytes) {
        this.totalBytes = totalBytes;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public com.github.misterchangray.core.annotation.MagicField getMagicField() {
        return magicField;
    }

    public void setMagicField(com.github.misterchangray.core.annotation.MagicField magicField) {
        this.magicField = magicField;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

}
