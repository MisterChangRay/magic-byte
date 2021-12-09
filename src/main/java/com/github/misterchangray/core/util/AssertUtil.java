package com.github.misterchangray.core.util;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.exception.MagicCollectionSizeNotMatchException;
import com.github.misterchangray.core.exception.MagicUnpackValueNotBeNullException;
import com.github.misterchangray.core.metainfo.ClassMetaInfo;
import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class AssertUtil {

    public static void assertEncoding(String encoding) {
        try {
            "".getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new MagicByteException(String.format("UnsupportedEncoding; %s", encoding));
        }
    }


    public static void assertHasLength(FieldMetaInfo fieldMetaInfo) {
        MagicField magicField1 = fieldMetaInfo.getMagicField();
        if(magicField1.size() <= 0 && magicField1.dynamicSizeOf() <= 0)
            throw new MagicByteException(String.format("field must set size of field: %s.%s", fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
    }


    public static void assertFieldsSortIsRight(List<FieldMetaInfo> res) {
        Map<Integer, FieldMetaInfo> tmp = new HashMap<Integer, FieldMetaInfo>(30);

        for(FieldMetaInfo fieldMetaInfo : res) {
            int order = fieldMetaInfo.getMagicField().order();
            if(order < 0) {
                throw new MagicByteException(String.format("order number should be start with one; %s", fieldMetaInfo.getField().getName()));
            }
            if(null != tmp.get(order)) {
                throw new MagicByteException(String.format("Sorting cannot be repeated; %s", fieldMetaInfo.getField().getName()));
            }

            if(TypeEnum.ARRAY == fieldMetaInfo.getType()  || TypeEnum.LIST == fieldMetaInfo.getType() || TypeEnum.STRING == fieldMetaInfo.getType()) {
                // 是否设置成员数量
                if(0 > fieldMetaInfo.getMagicField().size() && 0 > fieldMetaInfo.getMagicField().dynamicSizeOf()) {
                    throw new MagicByteException(String.format("list and array member size must be set ; %s", fieldMetaInfo.getField().getName()));
                }

                // 检查目标字段是否先初始化
                if(0 < fieldMetaInfo.getMagicField().dynamicSizeOf() && null == tmp.get(fieldMetaInfo.getMagicField().dynamicSizeOf())) {
                    throw new MagicByteException(String.format("dynamicSizeOf number should be less than target field order number; %s",
                            fieldMetaInfo.getField().getName()));
                }

                // 检查目标字段是否为整数
                if(0 < fieldMetaInfo.getMagicField().dynamicSizeOf() && (
                        TypeEnum.BYTE != fieldMetaInfo.getDynamicRef().getType() &&
                        TypeEnum.SHORT != fieldMetaInfo.getDynamicRef().getType() &&
                        TypeEnum.INT != fieldMetaInfo.getDynamicRef().getType()
                )) {
                    throw new MagicByteException(String.format("dynamicSizeOf target field should be number (int,short,byte); %s",
                            fieldMetaInfo.getField().getName()));
                }
            } else {
                if(fieldMetaInfo.getMagicField().dynamicSizeOf() > 0) {
                    throw new MagicByteException(String.format("dynamicSizeOf properties only use String, Array, List : %s.%s",
                            fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
                }
            }

            tmp.put(order, fieldMetaInfo);
        }

    }

    public static void assertTotalLengthNotZero(int total, ClassMetaInfo classMetaInfo) {
        if(classMetaInfo.isStrict() && total <= 0) {
            throw new MagicByteException(String.format("class total bytes is zero: %s", classMetaInfo.getClazz().getName()));
        }
    }


    public static void assertDataError(boolean interrupt, ClassMetaInfo classMetaInfo) {
        if(interrupt && classMetaInfo.isStrict()) {
            throw new MagicByteException(String.format("invalid byte array; do not converter to the java object : %s",
                    classMetaInfo.getClazz().getName()));
        }
    }

    public static void assertFieldMetaInfoInitSuccess(boolean initRes, FieldMetaInfo fieldMetaInfo) {
        if(!initRes && fieldMetaInfo.getOwnerClazz().isStrict()) {
            throw new MagicByteException(String.format("can't parser field of class: %s.%s",
                    fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
        }
    }

    public static void assertHasMagicField(FieldMetaInfo fieldMetaInfo) {
        if(Objects.isNull(fieldMetaInfo.getMagicField()) && fieldMetaInfo.getOwnerClazz().isStrict()) {
            throw new MagicByteException(String.format("not found @MagicField annotation of class: %s.%s",
                    fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
        }
    }



    public static void assertSizeOrDynamicOf(FieldMetaInfo fieldMetaInfo) {
        if(fieldMetaInfo.getMagicField().size() > 0 && fieldMetaInfo.getMagicField().dynamicSizeOf() > 0) {
            throw new MagicByteException(String.format("@MagicField(size, dynamicSizeOf) only can be use only one, annotation of class: %s.%s",
                    fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
        }
    }

    public static void assertLengthEqualsDeclare(FieldMetaInfo fieldMetaInfo, List objectList, int size) {
        if(objectList.size() < size) {
            throw new MagicCollectionSizeNotMatchException(String.format("collection actually length less than declare size , annotation of class: %s.%s",
                    fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
        }
    }

    public static void assertFieldValNonNull(FieldMetaInfo fieldMetaInfo, Object val) {
        if(Objects.isNull(val)) {
            throw new MagicUnpackValueNotBeNullException(String.format("properties can't be null, annotation of class: %s.%s",
                    fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
        }
    }
}
