package com.github.misterchangray.core.metainfo;


import com.github.misterchangray.core.annotation.MagicClass;

import java.lang.annotation.Annotation;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Optional;

public class ClassMetaInfo {
    private Class<?> clazz;
    private List<FieldMetaInfo> fields;
    private int totalBytes;
    private ByteOrder byteOrder;
    private boolean autoTrim;
    /**
     * use strict
+     */
    private boolean strict;
    private int[] dynamicSize;
    private boolean isDynamic;

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public int[] getDynamicSize() {
        return dynamicSize;
    }

    public void setDynamicSize(int[] dynamicSize) {
        this.dynamicSize = dynamicSize;
    }

    public FieldMetaInfo getByOrderId(int orderId) {
        Optional<FieldMetaInfo> first = fields.stream().filter(item -> item.getOrderId() == orderId).findFirst();
        return first.orElse(null);
    }


    public void initConfig(Class<?> clazz) {
        Annotation annotation = clazz.<MagicClass>getAnnotation(MagicClass.class);
        if(null != annotation) {
            MagicClass magicClass = (MagicClass) annotation;
            ByteOrder byteOrder =
                    magicClass.byteOrder() == com.github.misterchangray.core.enums.ByteOrder.LITTLE_ENDIAN ?
                            ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
            this.setByteOrder(byteOrder);;
            this.setAutoTrim(magicClass.autoTrim());
            this.setStrict(magicClass.strict());
        }
    }





    public boolean isAutoTrim() {
        return autoTrim;
    }

    public void setAutoTrim(boolean autoTrim) {
        this.autoTrim = autoTrim;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<FieldMetaInfo> getFields() {
        return fields;
    }

    public void setFields(List<FieldMetaInfo> fields) {
        this.fields = fields;
    }

    public int getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(int totalBytes) {
        this.totalBytes = totalBytes;
    }

    public boolean isStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

}
