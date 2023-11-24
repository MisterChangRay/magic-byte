package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.enums.ByteOrder;

import java.nio.charset.Charset;

public class GlobalConfigs {
    private static Charset GLOBAL_DEFAULT_CHARSET = Charset.forName("ASCII");

    private static ByteOrder GLOBAL_DEFAULT_BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    public static Charset getGlobalDefaultCharset() {
        return GLOBAL_DEFAULT_CHARSET;
    }

    public static void setGlobalDefaultCharset(Charset charset) {
        GLOBAL_DEFAULT_CHARSET = charset;
    }

    public static ByteOrder getGlobalDefaultByteOrder() {
        return GLOBAL_DEFAULT_BYTE_ORDER;
    }

    /**
     * 设置全局默认端序
     * <p>注：如果传入的类型是 AUTO 则自动转为 BIG_ENDIAN</p>
     *
     * @param globalDefaultByteOrder 端序
     */
    public static void setGlobalDefaultByteOrder(ByteOrder globalDefaultByteOrder) {
        if (globalDefaultByteOrder == ByteOrder.AUTO) {
            globalDefaultByteOrder = ByteOrder.BIG_ENDIAN;
        }
        GLOBAL_DEFAULT_BYTE_ORDER = globalDefaultByteOrder;
    }
}
