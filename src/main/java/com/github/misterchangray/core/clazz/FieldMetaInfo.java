package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.intf.MField;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.intf.MWriter;

import java.lang.reflect.Field;

public class FieldMetaInfo implements MField {
    private ClassMetaInfo ownerClazz;
    private MagicField magicField;
    private Field field;

    private MWriter writer;
    private MReader reader;

    /**
     * 字段大小,
     * 字符串指字节数
     * 数组指大小
     */
    private int size;


    /**
     * 每个元素的字节数
     */
    private int elementBytes;

    /**
     * 字段类型
     */
    private TypeEnum type;
    /**
     * 字段class
     */
    private Class<?> clazz;
    /**
     * 字符集
     */
    private String charset;

    /**
     * 当前字段是否为动态的
     * 只有字符串和数组能为动态
     */
    private boolean isDynamic;
    /**
     * 动态字段引用
     *
     */
    private FieldMetaInfo dynamicRef;

    /**
     * 字段序号
     */
    private int orderId;

    /**
     *
     */
    private int dynamicSizeOf;

    /**
     * 全限定名
     */
    private String fullName;

    /**
     * 默认值
     */
    private int defaultVal;


    /**
     * 泛型字段
     *
     */
    private FieldMetaInfo genericsField;



    /**
     * 自动计算长度
     * @return
     */
    private boolean calcLength;

    /**
     * 自动计算校验和字段
     * @return
     */
    private boolean calcCheckCode;

    /**
     * 自动裁剪
     * 当整个数据只有一个可变数据项时可使用。因为其他数据项长度固定。所以可以反推出可变数据长度
     * 不建议使用此配置, 序列化很影响性能。
     * @return
     */
    private boolean autoTrim;

    public boolean isDynamicSizeOf() {
        return dynamicSizeOf > -1;
    }

    public boolean isCalcLength() {
        return calcLength;
    }

    public void setCalcLength(boolean calcLength) {
        this.calcLength = calcLength;
    }

    public boolean isCalcCheckCode() {
        return calcCheckCode;
    }

    public void setCalcCheckCode(boolean calcCheckCode) {
        this.calcCheckCode = calcCheckCode;
    }

    public boolean isAutoTrim() {
        return autoTrim;
    }

    public void setAutoTrim(boolean autoTrim) {
        this.autoTrim = autoTrim;
    }

    public int getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(int defaultVal) {
        this.defaultVal = defaultVal;
    }

    public FieldMetaInfo getGenericsField() {
        return genericsField;
    }

    public void setGenericsField(FieldMetaInfo genericsField) {
        this.genericsField = genericsField;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getDynamicSizeOf() {
        return dynamicSizeOf;
    }

    public void setDynamicSizeOf(int dynamicSizeOf) {
        this.dynamicSizeOf = dynamicSizeOf;
    }

    public ClassMetaInfo getOwnerClazz() {
        return ownerClazz;
    }

    public void setOwnerClazz(ClassMetaInfo ownerClazz) {
        this.ownerClazz = ownerClazz;
    }

    public MagicField getMagicField() {
        return magicField;
    }

    public void setMagicField(MagicField magicField) {
        this.magicField = magicField;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getElementBytes() {
        return elementBytes;
    }

    public void setElementBytes(int elementBytes) {
        this.elementBytes = elementBytes;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public FieldMetaInfo getDynamicRef() {
        return dynamicRef;
    }

    public void setDynamicRef(FieldMetaInfo dynamicRef) {
        this.dynamicRef = dynamicRef;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public MReader getReader() {
        return this.reader;
    }

    @Override
    public MWriter getWriter() {
        return this.writer;
    }

    public void setWriter(MWriter writer) {
        this.writer = writer;
    }

    public void setReader(MReader reader) {
        this.reader = reader;
    }
}
