package com.github.misterchangray.core;

@FunctionalInterface
public interface MagicChecker {
    long calcCheckCode(byte[] data);
}
