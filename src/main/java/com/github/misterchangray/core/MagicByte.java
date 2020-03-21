package com.github.misterchangray.core;

import com.github.misterchangray.core.util.PackUtil;
import com.github.misterchangray.core.util.UnpackUtil;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;


public class MagicByte {

    public static <T> T pack(byte[] data, Class clazz) throws UnsupportedEncodingException, InstantiationException {
        return PackUtil.packObject(data, clazz);
    }

    public static <T> ByteBuffer unpack(T t) throws UnsupportedEncodingException {
        if (null == t) return null;

        return UnpackUtil.unpackObject(t);
    }


    public static <T> byte[] unpackToByte(T t) throws UnsupportedEncodingException {
        if (null == t) return null;

        return UnpackUtil.unpackObject(t).array();
    }
}
