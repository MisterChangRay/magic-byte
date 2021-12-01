package com.github.misterchangray.core.metainfo;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;

import java.lang.reflect.Field;

public class FieldMetaInfo {
    private ClassMetaInfo ownerClazz;
    private MagicField magicField;
    private Field field;
    private int size;

    private int totalBytes;

    // the size of one element on collections
    private int elementBytes;

    private TypeEnum type;
    private Class<?> clazz;
    private String charset;
    private boolean autoTrim;
    /**
     * current field the size is dynamic
     */
    private boolean isDynamic;
    /**
     * dynamic ref
     */
    private FieldMetaInfo dynamicRef;

    private int orderId;


    public int getElementBytes() {
        return elementBytes;
    }

    public void setElementBytes(int elementBytes) {
        this.elementBytes = elementBytes;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public FieldMetaInfo getDynamicRef() {
        return dynamicRef;
    }

    public void setDynamicRef(FieldMetaInfo dynamicRef) {
        this.dynamicRef = dynamicRef;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public ClassMetaInfo getOwnerClazz() {
        return ownerClazz;
    }

    public void setOwnerClazz(ClassMetaInfo ownerClazz) {
        this.ownerClazz = ownerClazz;
    }

    public boolean isAutoTrim() {
        return autoTrim;
    }

    public void setAutoTrim(boolean autoTrim) {
        this.autoTrim = autoTrim;
    }

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

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
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
