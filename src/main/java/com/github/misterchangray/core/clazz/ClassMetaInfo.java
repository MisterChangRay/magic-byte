package com.github.misterchangray.core.clazz;


import com.github.misterchangray.core.intf.MClass;

import java.nio.ByteOrder;
import java.util.List;

public class ClassMetaInfo implements MClass {
    private Class<?> clazz;
    private List<FieldMetaInfo> fields;
    private ByteOrder byteOrder;

    /**
     * 是否为动态大小
     */
    private boolean isDynamic;


    /**
     * 每个元素占用字节数量
     *
     * 总字节数 = size * elementBytes
     */
    private int elementBytes;


    /**
     * 全限定名
     */
    private String fullName;

    /**
     * 是否适用严格模式
     */
    private boolean strict;

    public boolean isStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    @Override
    public FieldMetaInfo getFieldMetaInfoByOrderId(int orderId) {
        for (FieldMetaInfo field : this.getFields()) {
            if( field.getOrderId() == orderId) {
                return field;
            }
        }
        return null;
    }

    public void setFields(List<FieldMetaInfo> fields) {
        this.fields = fields;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }


    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public int getElementBytes() {
        return elementBytes;
    }

    public void setElementBytes(int elementBytes) {
        this.elementBytes = elementBytes;
    }
}
