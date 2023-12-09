package com.github.misterchangray.core.enums;


/**
 * 字节序
 */
public enum ByteOrder {
    /**
     * 大端序
     */
    BIG_ENDIAN(java.nio.ByteOrder.BIG_ENDIAN),

    /**
     * 小端序
     */
    LITTLE_ENDIAN(java.nio.ByteOrder.LITTLE_ENDIAN),

    /**
     * 自动模式，应用全局默认端序
     */
    AUTO(null)
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
