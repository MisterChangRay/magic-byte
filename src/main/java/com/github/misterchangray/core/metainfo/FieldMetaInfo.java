package com.github.misterchangray.core.metainfo;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.util.ClassUtil;
import com.github.misterchangray.core.util.CollectionUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class FieldMetaInfo {
    private ClassMetaInfo ownerClazz;
    private MagicField magicField;
    private Field field;
    private int size;

    // the size of element
    // if element on collections, size is bytes of unit
    // otherwise object is total bytes
    private int elementBytes;

    private TypeEnum type;
    private Class<?> clazz;
    private String charset;
    private boolean autoTrim;
    /**
     * current field the size is dynamic
     */
    private boolean isDynamic;
    /**
     * dynamic ref
     */
    private FieldMetaInfo dynamicRef;

    private int orderId;


    public Class<?> getGenericClazz() {
        if(this.getType() == TypeEnum.ARRAY) {
            return this.getField().getType().getComponentType();

        } else
        if( this.getType() == TypeEnum.LIST) {
            Type genericType = this.getField().getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;
                return  (Class<?>)pt.getActualTypeArguments()[0];
            }        }
        return null;
    }



    public int calcDynamicSize(Object obj, Object val) {

        int size = 0;
        switch (this.getType()) {
            case ARRAY:
            case LIST:
                if(this.getSize() > 0) {
                    return this.getSize();
                }
                size = CollectionUtil.sizeOfCollection(this, val);
                break;
            case STRING:
                if(this.getElementBytes() > 0) {
                    return this.getElementBytes();
                }
                try {
                    size = ((String) val).getBytes(this.getCharset()).length;
                } catch (UnsupportedEncodingException e) {}
                break;
        }

        int c1 = ClassUtil.readAsInt(this.getDynamicRef(), obj);
        if(c1 > 0){
            size = c1;
        }

        ClassUtil.autoSetInt(obj, size, this.getDynamicRef());
        return size;
    }

    public int getElementBytes() {
        return elementBytes;
    }

    public void setElementBytes(int elementBytes) {
        this.elementBytes = elementBytes;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public FieldMetaInfo getDynamicRef() {
        return dynamicRef;
    }

    public void setDynamicRef(FieldMetaInfo dynamicRef) {
        this.dynamicRef = dynamicRef;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public ClassMetaInfo getOwnerClazz() {
        return ownerClazz;
    }

    public void setOwnerClazz(ClassMetaInfo ownerClazz) {
        this.ownerClazz = ownerClazz;
    }

    public boolean isAutoTrim() {
        return autoTrim;
    }

    public void setAutoTrim(boolean autoTrim) {
        this.autoTrim = autoTrim;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public com.github.misterchangray.core.annotation.MagicField getMagicField() {
        return magicField;
    }

    public void setMagicField(com.github.misterchangray.core.annotation.MagicField magicField) {
        this.magicField = magicField;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }


}
