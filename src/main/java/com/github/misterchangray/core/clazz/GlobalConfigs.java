package com.github.misterchangray.core.clazz;

import java.nio.charset.Charset;

public class GlobalConfigs {
    private static Charset GLOBAL_DEFAULT_CHARSET = Charset.forName("ASCII");

    public static Charset getGlobalDefaultCharset() {
        return GLOBAL_DEFAULT_CHARSET;
    }

    public static void setGlobalDefaultCharset(Charset charset) {
        GLOBAL_DEFAULT_CHARSET = charset;
    }
}
