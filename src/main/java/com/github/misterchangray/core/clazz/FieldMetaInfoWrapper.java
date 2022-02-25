package com.github.misterchangray.core.clazz;


public class FieldMetaInfoWrapper {
    private FieldMetaInfo fieldMetaInfo;
    private int startOffset;

    public FieldMetaInfoWrapper(FieldMetaInfo fieldMetaInfo, int startOffset) {
        this.fieldMetaInfo = fieldMetaInfo;
        this.startOffset = startOffset;
    }

    public FieldMetaInfo getFieldMetaInfo() {
        return fieldMetaInfo;
    }

    public void setFieldMetaInfo(FieldMetaInfo fieldMetaInfo) {
        this.fieldMetaInfo = fieldMetaInfo;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }
}
