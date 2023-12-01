package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.InvalidTypeException;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.intf.impl.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description: class manager
 * @author: Ray.chang
 * @create: 2021-12-17 15:11
 **/
public class TypeManager {

    /**
     * fast get class type
     */
    public static Map<Class<?>, TypeEnum> SUPPORTED_TYPES = new HashMap<Class<?>, TypeEnum>(20);
    static {
        for (TypeEnum value : TypeEnum.values()) {
            for (Class<?> type : value.getTypes()) {
                SUPPORTED_TYPES.put(type, value);
            }
        }
    };


    /**
     * get type of class
     * @param clazz
     * @return
     */
    public static TypeEnum getType(Class<?> clazz) {
        TypeEnum res = null;
        res = SUPPORTED_TYPES.get(clazz);
        if(Objects.nonNull(res)) {
            return res;
        } else if(clazz.isArray()) {
            res = TypeEnum.ARRAY;
        } else if(List.class.isAssignableFrom(clazz)) {
            res = TypeEnum.LIST;
        } else {
            res = TypeEnum.OBJECT;
        }

        return res;
    }


    /**
     * get type of class
     * @param fieldMetaInfo
     * @return
     */
    public static MReader newReader(FieldMetaInfo fieldMetaInfo) {
        MReader res = null;
        switch (fieldMetaInfo.getType()) {
            case BYTE:
                 res = new ByteReader(fieldMetaInfo);
                 break;
            case BOOLEAN:
                res = new BooleanReader(fieldMetaInfo);
                break;
            case CHAR:
                res = new CharReader(fieldMetaInfo);
                break;
            case INT:
                res = new IntReader(fieldMetaInfo);
                break;
            case SHORT:
                res = new ShortReader(fieldMetaInfo);
                break;
            case LONG:
                res = new LongReader(fieldMetaInfo);
                break;
            case FLOAT:
                res = new FloatReader(fieldMetaInfo);
                break;
            case DOUBLE:
                res = new DoubleReader(fieldMetaInfo);
                break;
            case STRING:
                res = new StringReader(fieldMetaInfo);
                break;
            case ARRAY:
            case LIST:
                res = new CollectionReader(fieldMetaInfo);
                break;
            case UBYTE:
                res = new UByteReader(fieldMetaInfo);
                break;
            case USHORT:
                res = new UShortReader(fieldMetaInfo);
                break;
            case UINT:
                res = new UIntReader(fieldMetaInfo);
                break;
            case ULONG:
                res = new ULongReader(fieldMetaInfo);
                break;
            case UNUMBER:
                res = new UNumberReader(fieldMetaInfo);
                break;
            case CUSTOM:
                res = new CustomReader(fieldMetaInfo);
                break;
            case DATETIME:
                res = new DateTimeReader(fieldMetaInfo);
                break;
            case OBJECT:
                res = new ObjectReader(fieldMetaInfo);
                break;
        }
        return res;
    }

    /**
     * get type of class
     * @param fieldMetaInfo
     * @return
     */
    public static MWriter newWriter(FieldMetaInfo fieldMetaInfo) {
        MWriter res = null;
        switch (fieldMetaInfo.getType()) {
            case BYTE:
                res = new ByteWriter(fieldMetaInfo);
                break;
            case BOOLEAN:
                res = new BooleanWriter(fieldMetaInfo);
                break;
            case CHAR:
                res = new CharWriter(fieldMetaInfo);
                break;
            case INT:
                res = new IntWriter(fieldMetaInfo);
                break;
            case SHORT:
                res = new ShortWriter(fieldMetaInfo);
                break;
            case LONG:
                res = new LongWriter(fieldMetaInfo);
                break;
            case FLOAT:
                res = new FloatWriter(fieldMetaInfo);
                break;
            case DOUBLE:
                res = new DoubleWriter(fieldMetaInfo);
                break;
            case STRING:
                res = new StringWriter(fieldMetaInfo);
                break;
            case ARRAY:
            case LIST:
                res = new CollectionWriter(fieldMetaInfo);
                break;
            case DATETIME:
                res = new DateTimeWriter(fieldMetaInfo);
                break;
            case UBYTE:
                res = new UByteWriter(fieldMetaInfo);
                break;
            case USHORT:
                res = new UShortWriter(fieldMetaInfo);
                break;
            case UINT:
                res = new UIntWriter(fieldMetaInfo);
                break;
            case ULONG:
                res = new ULongWriter(fieldMetaInfo);
                break;
            case UNUMBER:
                res = new UNumberWriter(fieldMetaInfo);
                break;
            case CUSTOM:
                res = new CustomWriter(fieldMetaInfo);
                break;
            case OBJECT:
                res = new ObjectWriter(fieldMetaInfo);
                break;
        }
        return res;
    }

    public static boolean isCollection(TypeEnum type) {
        return type.is( TypeEnum.LIST, TypeEnum.ARRAY);
    }

    public static boolean isVariable(TypeEnum type) {
        return type.is(TypeEnum.STRING, TypeEnum.LIST, TypeEnum.ARRAY);
    }


    public static Class<?> getGenericsFieldType(FieldMetaInfo origin) {
        if(origin.getType() == TypeEnum.ARRAY) {
            if(origin.getField().getType().getName().startsWith("[[")) {
                throw new InvalidTypeException("not support matrix, such as int[][]; at: " + origin.getFullName());
            }
            return origin.getField().getType().getComponentType();
        }


        Type genericType = origin.getField().getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            if(pt.getActualTypeArguments()[0] instanceof ParameterizedType) {
                throw new InvalidTypeException("not support matrix, such as List<List<String>>; at: " + origin.getFullName());
            }
            return (Class<?>)pt.getActualTypeArguments()[0];
        }
        return null;
    }
}
