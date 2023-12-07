package com.github.misterchangray.core;

@FunctionalInterface
public interface MagicChecker {
    /**
     * 计算校验和
     *
     * @param data 完整的字节数据
     * @return 返回校验和的字节数组, 即使一个字节也要封装为字节返回
     */
    byte[] calcCheckCode(byte[] data);
}
