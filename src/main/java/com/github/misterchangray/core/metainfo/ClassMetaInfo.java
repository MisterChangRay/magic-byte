package com.github.misterchangray.core.metainfo;


import com.github.misterchangray.core.annotation.MagicClass;

import java.lang.annotation.Annotation;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Optional;

public class ClassMetaInfo {
    private Class<?> clazz;
    private List<FieldMetaInfo> fields;
    private ByteOrder byteOrder;
    private boolean autoTrim;
    /**
     * use strict
+     */
    private boolean strict;
    private boolean isDynamic;



    // the size of element
    // if element on collections, size is bytes of unit
    // otherwise object is total bytes
    private int elementBytes;


    public int getElementBytes() {
        return elementBytes;
    }

    public void setElementBytes(int elementBytes) {
        this.elementBytes = elementBytes;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public FieldMetaInfo getByOrderId(int orderId) {
        Optional<FieldMetaInfo> first = fields.stream().filter(item -> item.getOrderId() == orderId).findFirst();
        return first.orElse(null);
    }


    public void initConfig(Class<?> clazz) {
        Annotation annotation = clazz.<MagicClass>getAnnotation(MagicClass.class);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;

        if(null != annotation) {
            MagicClass magicClass = (MagicClass) annotation;
            byteOrder = magicClass.byteOrder() == com.github.misterchangray.core.enums.ByteOrder.LITTLE_ENDIAN ?
                            ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
            this.setAutoTrim(magicClass.autoTrim());
            this.setStrict(magicClass.strict());
        }
        this.setByteOrder(byteOrder);;

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

    public boolean isStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

}
