package com.github.misterchangray.core.metainfo;


import java.nio.ByteOrder;
import java.util.List;

public class ClassMetaInfo {
    private Class clazz;
    private List<FieldMetaInfo> fields;
    private int totalBytes;
    private ByteOrder byteOrder;
    private boolean autoTrim;

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

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
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

}
