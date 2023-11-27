package com.github.misterchangray.core;

@FunctionalInterface
public interface MagicChecker {
    byte[] calcCheckCode(byte[] data);
}
