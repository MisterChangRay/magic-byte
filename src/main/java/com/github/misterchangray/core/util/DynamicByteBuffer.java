package com.github.misterchangray.core.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class DynamicByteBuffer {
    private ByteBuffer byteBuffer;
    private boolean isDynamic;
    private static final int STEP  = 1024;

    public static DynamicByteBuffer allocate(int bytes) {
        DynamicByteBuffer dynamicByteBuffer = new DynamicByteBuffer();
        dynamicByteBuffer.isDynamic =false;
        dynamicByteBuffer.byteBuffer = ByteBuffer.allocate(bytes);
        return dynamicByteBuffer;
    }

    public static DynamicByteBuffer allocate() {
        DynamicByteBuffer dynamicByteBuffer = new DynamicByteBuffer();
        dynamicByteBuffer.isDynamic = true;
        dynamicByteBuffer.byteBuffer =ByteBuffer.allocate(STEP);
        return dynamicByteBuffer;
    }

    private void autoGrow(int bytes) {
        if(!this.isDynamic) {
            return;
        }

        if( this.byteBuffer.capacity() - this.byteBuffer.position() >= bytes) {
            return;
        }

        byteBuffer = ByteBuffer.allocate(byteBuffer.capacity() * 2).order(this.byteBuffer.order());
    }

    public DynamicByteBuffer order(ByteOrder order) {
        this.byteBuffer.order(order);
        return this;
    }

    
    public byte get() {
        return byteBuffer.get();
    }



    
    public ByteBuffer put(byte b) {
        autoGrow(1);
        return this.byteBuffer.put(b);
    }

    
    public byte get(int i) {
        return this.byteBuffer.get(i);
    }

    public char getChar() {
        return this.byteBuffer.getChar();
    }

    
    public ByteBuffer putChar(char c) {
        autoGrow(2);
        return this.byteBuffer.putChar(c);
    }


    public short getShort() {
        return this.byteBuffer.getShort();
    }

    
    public ByteBuffer putShort(short i) {
        autoGrow(2);
        return this.byteBuffer.putShort(i);
    }




    public int getInt() {
        return this.byteBuffer.getInt();
    }

    
    public ByteBuffer putInt(int i) {
        autoGrow(4);
        return this.byteBuffer.putInt(i);
    }




    public long getLong() {
        return this.byteBuffer.getLong();
    }

    
    public ByteBuffer putLong(long l) {
        autoGrow(8);
        return this.byteBuffer.putLong(l);
    }


    public float getFloat() {
        return this.byteBuffer.getFloat();
    }

    
    public ByteBuffer putFloat(float v) {
        autoGrow(4);
        return this.byteBuffer.putFloat(v);
    }



    public double getDouble() {
        return this.byteBuffer.getDouble();
    }

    
    public ByteBuffer putDouble(double v) {
        autoGrow(8);
        return this.byteBuffer.putDouble(v);
    }


    public byte[] array() {
        byte[] re= new byte[this.byteBuffer.position()];
        this.byteBuffer.flip();
       this.byteBuffer.get(re);
        return re;
    }

    public void put(byte[] bytes) {
        autoGrow(bytes.length);
        this.byteBuffer.put(bytes);
    }
}
