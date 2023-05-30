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
