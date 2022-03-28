package com.github.misterchangray.core.clazz;


import com.github.misterchangray.core.intf.MClass;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassMetaInfo implements MClass {
    private Class<?> clazz;
    private List<FieldMetaInfo> fields;
    private ByteOrder byteOrder;

    /**
     * 类嵌套
     *
     * 值为上级信息
     */
    private ClassMetaInfo parent;

    /**
     * 展开模式下的所有字段
     *
     * 只有最上级有维护此值
     */
    private List<FieldMetaInfo> flatFields = new ArrayList<>();

    /**
     * 获取根节点信息
     */
    private ClassMetaInfo root;

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
     * 是否使用严格模式
     */
    private boolean strict;

    /**
     * 获取根节点
     * @return
     */
    public ClassMetaInfo getRoot() {
        if(Objects.nonNull(root)) {
            return root;
        }
        ClassMetaInfo self = this, parent = this.parent;
        for (; Objects.nonNull(parent) ;) {
            self = parent;
            parent = self.parent;
        }

        this.root = self;
        return self;
    }

    public List<FieldMetaInfo> getFlatFields() {
        return flatFields;
    }

    public void setFlatFields(List<FieldMetaInfo> flatFields) {
        this.flatFields = flatFields;
    }

    public ClassMetaInfo getParent() {
        return parent;
    }

    public void setParent(ClassMetaInfo parent) {
        this.parent = parent;
    }
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
