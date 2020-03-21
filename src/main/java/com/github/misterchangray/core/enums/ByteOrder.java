package com.github.misterchangray.core.enums;


public enum ByteOrder {
    BIG_ENDIAN(java.nio.ByteOrder.BIG_ENDIAN),
    LITTLE_ENDIAN(java.nio.ByteOrder.LITTLE_ENDIAN),
    ;

    private java.nio.ByteOrder bytes;

    ByteOrder(java.nio.ByteOrder bytes) {
        this.bytes = bytes;
    }

    public java.nio.ByteOrder getBytes() {
        return bytes;
    }

    public void setBytes(java.nio.ByteOrder bytes) {
        this.bytes = bytes;
    }
}
