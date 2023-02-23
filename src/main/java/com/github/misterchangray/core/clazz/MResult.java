package com.github.misterchangray.core.clazz;

public class MResult<T> {
    private Integer bytes;
    private T data;

    public Integer getBytes() {
        return bytes;
    }

    public MResult setBytes(Integer bytes) {
        this.bytes = bytes;
        return this;
    }

    public T getData() {
        return data;
    }

    public MResult setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> MResult build(Integer bytes, T data) {
        MResult result = new MResult();
        result.bytes = bytes;
        result.data = data;
        return result;
    }

    public static <T> MResult build( T data) {
        MResult result = new MResult();
        result.data = data;
        return result;
    }
}
