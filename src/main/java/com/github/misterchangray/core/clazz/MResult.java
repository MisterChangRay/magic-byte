package com.github.misterchangray.core.clazz;

/**
 * 自定义序列号返回实体类
 * @param <T>
 */
public class MResult<T> {
    /**
     * 读取/写入总字节数
     */
    private Integer bytes;
    /**
     * 读取返回的实体类
     */
    private T data;

    public Integer getBytes() {
        return bytes;
    }

    public MResult<T> setBytes(Integer bytes) {
        this.bytes = bytes;
        return this;
    }

    public T getData() {
        return data;
    }

    public MResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> MResult<T> build(Integer bytes, T data) {
        MResult<T> result = new MResult<>();
        result.bytes = bytes;
        result.data = data;
        return result;
    }

    public static <T> MResult<T> build(T data) {
        MResult<T> result = new MResult<>();
        result.data = data;
        return result;
    }
}
