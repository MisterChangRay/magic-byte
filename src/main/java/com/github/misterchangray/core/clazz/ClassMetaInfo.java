package com.github.misterchangray.core.clazz;


import com.github.misterchangray.core.intf.MClass;

import java.nio.ByteOrder;
import java.util.List;

public class ClassMetaInfo implements MClass {
    private Class<?> clazz;
    private List<FieldMetaInfo> fields;
    private ByteOrder byteOrder;
    private boolean autoTrim;

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
     * 填充字节
     * -1 则不启用
     */
    private byte fillByte;

    /**
     * 全限定名
     */
    private String fullName;



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public byte getFillByte() {
        return fillByte;
    }

    public void setFillByte(byte fillByte) {
        this.fillByte = fillByte;
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

    public boolean isAutoTrim() {
        return autoTrim;
    }

    public void setAutoTrim(boolean autoTrim) {
        this.autoTrim = autoTrim;
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
