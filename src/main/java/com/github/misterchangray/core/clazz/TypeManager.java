package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.intf.MWriter;
import com.github.misterchangray.core.intf.impl.*;

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
            SUPPORTED_TYPES.put(value.getType(), value);
            if(Objects.nonNull(value.getRawType())) {
                SUPPORTED_TYPES.put(value.getRawType(), value);
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
            case OBJECT:
                res = new ObjectWriter(fieldMetaInfo);
                break;
        }
        return res;
    }

    public static boolean isCollection(TypeEnum type) {
        return TypeEnum.LIST == type || TypeEnum.ARRAY == type;
    }

    public static boolean isVariable(TypeEnum type) {
        return TypeEnum.STRING == type || TypeEnum.LIST == type || TypeEnum.ARRAY == type;
    }
}
