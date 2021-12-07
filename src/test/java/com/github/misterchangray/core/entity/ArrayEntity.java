package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

public class ArrayEntity {
    @MagicField(order = 1, size = 3)
    private String[] strings;
    @MagicField(order = 2, size = 3)
    private List<String> strings2;

    @MagicField(order = 3, size = 3)
    private byte[] bytes;
    @MagicField(order = 4, size = 3)
    private List<Byte> bytes2;

    @MagicField(order = 5, size = 3)
    private int[] ints;
    @MagicField(order = 6, size = 3)
    private List<Integer> ints2;

    @MagicField(order = 7, size = 3)
    private long[] longs;
    @MagicField(order = 8, size = 3)
    private List<Long> longs2;

    @MagicField(order = 9, size = 3)
    private short[] shorts;
    @MagicField(order = 10, size = 3)
    private List<Short> shorts2;

    @MagicField(order = 11, size = 3)
    private char[] chars;
    @MagicField(order = 12, size = 3)
    private List<Character> chars2;

    @MagicField(order = 13, size = 3)
    private Phone[] phones;

    @MagicField(order = 14, size = 3)
    private List<Phone> phones2;


    public Phone[] getPhones() {
        return phones;
    }

    public void setPhones(Phone[] phones) {
        this.phones = phones;
    }

    public List<Phone> getPhones2() {
        return phones2;
    }

    public void setPhones2(List<Phone> phones2) {
        this.phones2 = phones2;
    }

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public List<String> getStrings2() {
        return strings2;
    }

    public void setStrings2(List<String> strings2) {
        this.strings2 = strings2;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public List<Byte> getBytes2() {
        return bytes2;
    }

    public void setBytes2(List<Byte> bytes2) {
        this.bytes2 = bytes2;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public List<Integer> getInts2() {
        return ints2;
    }

    public void setInts2(List<Integer> ints2) {
        this.ints2 = ints2;
    }

    public long[] getLongs() {
        return longs;
    }

    public void setLongs(long[] longs) {
        this.longs = longs;
    }

    public List<Long> getLongs2() {
        return longs2;
    }

    public void setLongs2(List<Long> longs2) {
        this.longs2 = longs2;
    }

    public short[] getShorts() {
        return shorts;
    }

    public void setShorts(short[] shorts) {
        this.shorts = shorts;
    }

    public List<Short> getShorts2() {
        return shorts2;
    }

    public void setShorts2(List<Short> shorts2) {
        this.shorts2 = shorts2;
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public List<Character> getChars2() {
        return chars2;
    }

    public void setChars2(List<Character> chars2) {
        this.chars2 = chars2;
    }
}
