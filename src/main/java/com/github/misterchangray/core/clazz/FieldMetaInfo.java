package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.ByteOrder;
import com.github.misterchangray.core.enums.TimestampFormatter;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.intf.MField;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.intf.MWriter;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

public class FieldMetaInfo implements MField {
    private ClassMetaInfo ownerClazz;
    private MagicField magicField;
    private Field field;

    /**
     * 访问路径
     */
    private String accessPath;

    private MWriter writer;
    private MReader reader;
    /**
     * 字段类型
     * 1 数据字段
     * 2 嵌套字段
     */
    private int fieldType;
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
     * 自定义序列化时 customType 储存字段类型
     */
    private TypeEnum customType;
    /**
     * 字段class
     */
    private Class<?> clazz;
    private ClassMetaInfo clazzMetaInfo;
    /**
     * 字符集
     */
    private Charset charset;

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
     * 这里使用 accessPath 进行引用
     */
    private String dynamicSizeOf;

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
     * 不建议使用此配置, 优先考虑使用 dynamicSizeOf。
     * @return
     */
    private boolean dynamicSize;

    /**
     * 如果当前字段为 dynamicSize,
     *
     * 此字段值为当前字段后续所有字段的总字节数
     */
    private int suffixBytes;

    /**
     * 如果当前字段有值则代表
     * 1. 当前字段为命令控制字段
     */
    private boolean cmdField;

    /**
     * 字段是否提供自定义序列化方法
     *
     *
     */
    private CustomConverterInfo customConverter ;

    /**
     * 日期格式化方式
     * 如果字段属性为日期类型时，可通过此字段控制序列化方式
     * 默认格式化到秒
     */
    private TimestampFormatter timestampFormatter;
    private String formatPattern;


    /**
     * 是否有自定义序列化申明
     */
    private boolean hasCustomConverter;

    /**
     * 字节序
     *
     * <p> 注：默认返回 AUTO，应用全局配置，如果类配置了使用类配置的端序
     */
    private ByteOrder byteOrder;

    public boolean isHasCustomConverter() {
        return hasCustomConverter;
    }

    public void setHasCustomConverter(boolean hasCustomConverter) {
        this.hasCustomConverter = hasCustomConverter;
    }
    public TypeEnum getCustomType() {
        return customType;
    }
    public TypeEnum getRealType() {
        if(Objects.nonNull(customType)) {
            return customType;
        }
        return type;
    }


    public void setCustomType(TypeEnum customType) {
        this.customType = customType;
    }



    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }



    public boolean isCmdField() {
        return cmdField;
    }

    public void setCmdField(boolean cmdField) {
        this.cmdField = cmdField;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public TimestampFormatter getTimestampFormatter() {
        return timestampFormatter;
    }

    public void setTimestampFormatter(TimestampFormatter timestampFormatter) {
        this.timestampFormatter = timestampFormatter;
    }

    public CustomConverterInfo getCustomConverter() {
        return customConverter;
    }

    public void setCustomConverter(CustomConverterInfo customConverter) {
        this.customConverter = customConverter;
    }

    public int getSuffixBytes() {
        return suffixBytes;
    }

    public void setSuffixBytes(int suffixBytes) {
        this.suffixBytes = suffixBytes;
    }

    public boolean isDynamicSizeOf() {
        return dynamicSizeOf.length() > 0;
    }

    public ClassMetaInfo getClazzMetaInfo() {
        return clazzMetaInfo;
    }

    public void setClazzMetaInfo(ClassMetaInfo clazzMetaInfo) {
        this.clazzMetaInfo = clazzMetaInfo;
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

    public boolean isDynamicSize() {
        return dynamicSize;
    }

    public void setDynamicSize(boolean dynamicSize) {
        this.dynamicSize = dynamicSize;
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

    public String getDynamicSizeOf() {
        return dynamicSizeOf;
    }

    public void setDynamicSizeOf(String dynamicSizeOf) {
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

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
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

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
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

    public boolean isCollection() {
        return this.type == TypeEnum.ARRAY || this.type == TypeEnum.LIST;
    }


    public void verifyCalcCheckCode() {
        if(this.isCalcCheckCode()) {
            boolean aValidType = this.type.is(TypeEnum.BYTE, TypeEnum.SHORT, TypeEnum.INT, TypeEnum.LONG,
                    TypeEnum.UBYTE, TypeEnum.USHORT, TypeEnum.UINT, TypeEnum.ULONG,
                    TypeEnum.ARRAY, TypeEnum.LIST);

            if(aValidType && this.isCollection()) {
                aValidType = this.isByteCollection();
            }
            if(!aValidType) {
                throw new InvalidParameterException("calcCheckCode field the type must be primitive and only be (byte, short, int, long) or byte[]; at: " + this.getFullName());
            }
        }

    }


    public boolean isByteCollection() {
        boolean isCollection = this.isCollection();
        boolean isByte = this.getGenericsField().getType().is(TypeEnum.BYTE ,TypeEnum.UBYTE);
        return isCollection && isByte;
    }

    public void verifyCalcLength() {
        if(this.isCalcLength()) {
            boolean aValidType = this.type.is(TypeEnum.BYTE, TypeEnum.SHORT, TypeEnum.INT, TypeEnum.UBYTE, TypeEnum.USHORT, TypeEnum.UINT);
            if( !aValidType) {
                throw new InvalidParameterException("calcLength field the type must be primitive and only be byte, short, int; at: " + this.getFullName());
            }
        }
    }

    public void configFieldType() {
        this.fieldType = 1;
        if(this.type.is(TypeEnum.OBJECT) || ( this.isCollection() && this.getGenericsField().type.is(TypeEnum.OBJECT))) {
            this.fieldType = 2;
        }
    }


    /**
     * 查找 dynamicSizeOf 对象
     *
     * @param classMetaInfo
     */
    public void linkDynamicSizeOf(ClassMetaInfo classMetaInfo) {
        if(!this.isDynamic || !this.isDynamicSizeOf()) {
            return;
        }
        if(!dynamicSizeOf.startsWith("#") && Objects.nonNull(this.dynamicRef)) {
            return;
        }

        List<FieldMetaInfo> flatFields = classMetaInfo.getFlatFields();;
        String dynamicSizeOf = this.dynamicSizeOf;

        if(dynamicSizeOf.startsWith("#")) {
            dynamicSizeOf = dynamicSizeOf.substring(1);
        }
        if(Objects.isNull(flatFields) || flatFields.size() == 0) {
            return;
        }
        FieldMetaInfo dynamicRefTmp = null;
        for (int i = flatFields.size() - 1; i >= 0; i--) {
            FieldMetaInfo flatField = flatFields.get(i);
            if(flatField.getAccessPath().equals(dynamicSizeOf)) {
                dynamicRefTmp = flatField;
            }
        }


        if(Objects.nonNull(dynamicRefTmp)) {
            this.setDynamicRef(dynamicRefTmp);
            dynamicRefTmp.setDynamicRef(this);
        }

    }

    public String getNameForRegister() {
        return this.getOwnerClazz().getClazz().getName() + '_' + this.getAccessPath();
    }
}
