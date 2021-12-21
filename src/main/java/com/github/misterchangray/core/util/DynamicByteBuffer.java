package com.github.misterchangray.core.util;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicParseException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @description: 动态的bytebuffer 可以自动扩容
 * @author: Ray.chang
 * @create: 2021-12-17 15:05
 **/
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
        int newSize = (byteBuffer.capacity() * 2) + bytes;
        ByteBuffer tmp = ByteBuffer.allocate(newSize).order(this.byteBuffer.order());
        tmp.put(this.array());
        this.byteBuffer = tmp;

    }

    public DynamicByteBuffer order(ByteOrder order) {
        this.byteBuffer.order(order);
        return this;
    }

    
    public byte get() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.BYTE.getBytes()) {
            throw new MagicParseException();
        }
        return byteBuffer.get();
    }



    
    public ByteBuffer put(byte b) {
        autoGrow(1);
        return this.byteBuffer.put(b);
    }

    
    public byte get(int i) {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.BYTE.getBytes()) {
            throw new MagicParseException();
        }
        return this.byteBuffer.get(i);
    }

    public char getChar() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.CHAR.getBytes()) {
            throw new MagicParseException();
        }
        return this.byteBuffer.getChar();
    }

    
    public ByteBuffer putChar(char c) {
        autoGrow(2);
        return this.byteBuffer.putChar(c);
    }


    public short getShort() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.SHORT.getBytes()) {
            throw new MagicParseException();
        }
        return this.byteBuffer.getShort();
    }

    
    public ByteBuffer putShort(short i) {
        autoGrow(2);
        return this.byteBuffer.putShort(i);
    }




    public int getInt() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.INT.getBytes()) {
            throw new MagicParseException();
        }
        return this.byteBuffer.getInt();
    }

    
    public ByteBuffer putInt(int i) {
        autoGrow(4);
        return this.byteBuffer.putInt(i);
    }




    public long getLong() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.LONG.getBytes()) {
            throw new MagicParseException();
        }
        return this.byteBuffer.getLong();
    }

    
    public ByteBuffer putLong(long l) {
        autoGrow(8);
        return this.byteBuffer.putLong(l);
    }


    public float getFloat() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.FLOAT.getBytes()) {
            throw new MagicParseException();
        }
        return this.byteBuffer.getFloat();
    }

    
    public ByteBuffer putFloat(float v) {
        autoGrow(4);
        return this.byteBuffer.putFloat(v);
    }



    public double getDouble() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.DOUBLE.getBytes()) {
            throw new MagicParseException();
        }
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


    public ByteBuffer buffer() {
        if(!this.isDynamic) {
            return this.byteBuffer;
        }

        byte[] array = this.array();
        return ByteBuffer.allocate(array.length).put(array);
    }

    public void put(byte[] bytes) {
        autoGrow(bytes.length);
        this.byteBuffer.put(bytes);
    }

    public void position(int i) {
        this.byteBuffer.position(i);
    }

    public int position() {
        return this.byteBuffer.position();
    }

    public void get(byte[] bytes) {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < bytes.length) {
            throw new MagicParseException();
        }
        this.byteBuffer.get(bytes);
    }

    public void fill(byte fillByte) {
        while (this.byteBuffer.position() < this.byteBuffer.capacity()) {
            this.byteBuffer.put(fillByte);
        }
        this.byteBuffer.position(0);
    }


}
