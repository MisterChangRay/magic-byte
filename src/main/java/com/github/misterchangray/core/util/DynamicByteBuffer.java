package com.github.misterchangray.core.util;

import com.github.misterchangray.core.MagicChecker;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfoWrapper;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicParseException;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * 动态的bytebuffer 可以自动扩容
 * 支持固定长度和非固定长度
 * @author: Ray.chang
 * @create: 2021-12-17 15:05
 **/
public class DynamicByteBuffer {
    private ByteBuffer byteBuffer;
    private boolean isDynamic;
    private static final int STEP  = 1024;

    private Map<String, FieldMetaInfoWrapper> delayCache = new HashMap<>();
    // 保存长度偏移, 方便滞后计算并重置值
    private String LENGTH_FIELD_WRAPPER = "LENGTH_FIELD_WRAPPER";
    // 保存 校验位 偏移, 方便滞后计算并重置值
    private String  CHECK_CODE_FIELD_WRAPPER = "CHECK_CODE_FIELD_WRAPPER";


    public static DynamicByteBuffer allocate(int bytes) {
        ExceptionUtil.throwIFOOM(bytes, "DynamicByteBuffer allocate field!");

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
        ExceptionUtil.throwIFOOM(newSize, "DynamicByteBuffer Auto Grow field!");

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
            throw new MagicParseException("Missing data");
        }
        return byteBuffer.get();
    }



    
    public ByteBuffer put(byte b) {
        autoGrow(1);
        return this.byteBuffer.put(b);
    }

    public ByteBuffer put(int index, byte b) {
        if(index >= this.position()) {
            return put(b);
        } else {
            return this.byteBuffer.put(index, b);
        }
    }


    public byte get(int i) {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.BYTE.getBytes()) {
            throw new MagicParseException("Missing data");
        }
        return this.byteBuffer.get(i);
    }

    public char getChar() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.CHAR.getBytes()) {
            throw new MagicParseException("Missing data");
        }
        return this.byteBuffer.getChar();
    }

    
    public ByteBuffer putChar(char c) {
        autoGrow(2);
        return this.byteBuffer.putChar(c);
    }

    public ByteBuffer putChar(int index, char c) {
        if(index >= this.position()) {
            return putChar(c);
        } else {
            return this.byteBuffer.putChar(index, c);
        }

    }


    public short getShort() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.SHORT.getBytes()) {
            throw new MagicParseException("Missing data");
        }
        return this.byteBuffer.getShort();
    }

    
    public ByteBuffer putShort(short i) {
        autoGrow(2);
        return this.byteBuffer.putShort(i);
    }


    public ByteBuffer putShort(int index, short i) {
        if(index >= this.position()) {
            return putShort(i);
        } else {
            return this.byteBuffer.putShort(index, i);

        }

    }



    public int getInt() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.INT.getBytes()) {
            throw new MagicParseException("Missing data");
        }
        return this.byteBuffer.getInt();
    }

    
    public ByteBuffer putInt(int i) {
        autoGrow(4);
        return this.byteBuffer.putInt(i);
    }

    public ByteBuffer putInt(int index, int i) {
        if(index >= this.position()) {
            return putInt(i);
        } else {
            return this.byteBuffer.putInt(index, i);
        }
    }



    public long getLong() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.LONG.getBytes()) {
            throw new MagicParseException("Missing data");
        }
        return this.byteBuffer.getLong();
    }

    
    public ByteBuffer putLong(long l) {
        autoGrow(8);
        return this.byteBuffer.putLong(l);
    }

    public ByteBuffer putLong(int index, long l) {
        if(index >= this.position()) {
            return putLong(l);
        } else {
            return this.byteBuffer.putLong(index, l);

        }

    }

    public float getFloat() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.FLOAT.getBytes()) {
            throw new MagicParseException("Missing data");
        }
        return this.byteBuffer.getFloat();
    }

    
    public ByteBuffer putFloat(float v) {
        autoGrow(4);
        return this.byteBuffer.putFloat(v);
    }


    public ByteBuffer putFloat(int index, float v) {
        if(index >= this.position()) {
            return putFloat(v);
        } else {
            return this.byteBuffer.putFloat(index, v);

        }

    }


    public double getDouble() {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < TypeEnum.DOUBLE.getBytes()) {
            throw new MagicParseException("Missing data");
        }
        return this.byteBuffer.getDouble();
    }

    
    public ByteBuffer putDouble(double v) {
        autoGrow(8);
        return this.byteBuffer.putDouble(v);
    }

    public ByteBuffer putDouble(int index, double v) {
        if(index >= this.position()) {
            return putDouble(v);
        } else {
            return this.byteBuffer.putDouble(index, v);
        }
    }

    public int capacity() {
        return this.byteBuffer.capacity();
    }

    public byte[] array() {
        byte[] re= new byte[this.byteBuffer.capacity() - this.byteBuffer.remaining()];
        this.byteBuffer.position(0);
        this.byteBuffer.get(re, 0, re.length);
        return re;
    }

    public byte[] bytes() {
      return this.byteBuffer.array();
    }


    public ByteBuffer buffer() {
        if(!this.isDynamic &&
                this.byteBuffer.capacity() == this.byteBuffer.position()) {
            return this.byteBuffer;
        }

        byte[] array = new byte[this.byteBuffer.position()];
        this.byteBuffer.flip();
        this.byteBuffer.get(array);
        return ByteBuffer.allocate(this.position())
                .order(this.byteBuffer.order())
                .put(array);
    }

    public void put(byte[] bytes) {
        autoGrow(bytes.length);
        this.byteBuffer.put(bytes);
    }

    public void position(int i) {
        this.byteBuffer.position(i);
    }

    public void flip() {
        this.byteBuffer.flip();
    }


    public int position() {
        return this.byteBuffer.position();
    }

    public void get(byte[] bytes) {
        if(this.byteBuffer.capacity() - this.byteBuffer.position() < bytes.length) {
            throw new MagicParseException("Missing data");
        }
        this.byteBuffer.get(bytes);
    }

    public void fill(byte fillByte) {
        while (this.byteBuffer.position() < this.byteBuffer.capacity()) {
            this.byteBuffer.put(fillByte);
        }
        this.byteBuffer.position(0);
    }


    public void setLengthFieldWrapper(FieldMetaInfo fieldMetaInfo) {
        if(fieldMetaInfo.isCalcLength()) {
            this.registerDelayWrapper(LENGTH_FIELD_WRAPPER,
                    new FieldMetaInfoWrapper(fieldMetaInfo, this.position(), null));
        }

    }

    public void registerDelayWrapper(String name, FieldMetaInfoWrapper fieldMetaInfo) {
        this.delayCache.put(name, fieldMetaInfo);
    }

    public void registerDelayWrapper(FieldMetaInfoWrapper fieldMetaInfoWrapper) {
        FieldMetaInfo fieldMetaInfo = fieldMetaInfoWrapper.getFieldMetaInfo();
        this.delayCache.put(fieldMetaInfo.getNameForRegister(), fieldMetaInfoWrapper);
    }

    public void setCheckCodeFieldWrapper(FieldMetaInfo fieldMetaInfo) {
        if(fieldMetaInfo.isCalcCheckCode()) {
            this.registerDelayWrapper(CHECK_CODE_FIELD_WRAPPER,
                    new FieldMetaInfoWrapper(fieldMetaInfo, this.position(), null));
        }
    }

    public Object delayCalc(FieldMetaInfo fieldMetaInfo) throws IllegalAccessException {
        return this.delayCalc(fieldMetaInfo.getNameForRegister());
    }
    public Object delayCalc(String name) throws IllegalAccessException {
        if(delayCache.containsKey(name)) {
            FieldMetaInfoWrapper fieldMetaInfoWrapper = this.delayCache.get(name);
            FieldMetaInfo fieldMetaInfo = fieldMetaInfoWrapper.getFieldMetaInfo();

            if(Objects.nonNull(fieldMetaInfoWrapper.getVal())) {
                return fieldMetaInfoWrapper.getVal();
            }

            int p = this.position();
            this.position(fieldMetaInfoWrapper.getStartOffset());
            Object o = fieldMetaInfo.getReader().readFormBuffer(this, null);
            this.position(p);
            return o;
        }
        return null;
    }

    public void delayCalc(MagicChecker magicChecker) throws IllegalAccessException {
        FieldMetaInfo fieldMetaInfo = null;
        if(delayCache.containsKey(LENGTH_FIELD_WRAPPER)) {
            FieldMetaInfoWrapper fieldMetaInfoWrapper = this.delayCache.get(LENGTH_FIELD_WRAPPER);
            fieldMetaInfo = fieldMetaInfoWrapper.getFieldMetaInfo();
            fieldMetaInfo.getWriter().writeToBuffer(this,   ConverterUtil.toTargetObject(fieldMetaInfo.getType(),
                            this.position()), null, fieldMetaInfoWrapper.getStartOffset());

        }
        if(delayCache.containsKey(CHECK_CODE_FIELD_WRAPPER) && Objects.nonNull(magicChecker)) {
            byte[] val = null;
            try {
                val = magicChecker.calcCheckCode(this.array());
            } catch (Exception ae) {
                throw ae;
            }
            FieldMetaInfoWrapper fieldMetaInfoWrapper = this.delayCache.get(CHECK_CODE_FIELD_WRAPPER);
            fieldMetaInfo = fieldMetaInfoWrapper.getFieldMetaInfo();


            Object data = null;
            if(!fieldMetaInfo.isCollection()) {
                val = Arrays.copyOfRange(val, 0, fieldMetaInfo.getElementBytes());
                data = ConverterUtil.toTargetObject(fieldMetaInfo.getType(), ConverterUtil.byteToNumber(val));
            } else {
                data = ConverterUtil.toTargetObject(fieldMetaInfo.getType(), fieldMetaInfo.getGenericsField().getType(), val);;
            }
            fieldMetaInfo.getWriter().writeToBuffer(this, data, null, fieldMetaInfoWrapper.getStartOffset());

        }
    }
}
