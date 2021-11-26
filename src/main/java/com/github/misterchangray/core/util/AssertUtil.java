package com.github.misterchangray.core.util;

import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.metainfo.ClassMetaInfo;
import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class AssertUtil {

    public static void assertSizeNotNull(FieldMetaInfo fieldMetaInfo) {
        MagicField magicField1 = fieldMetaInfo.getMagicField();
        if(magicField1.size() < 0)
            throw new MagicByteException(String.format("field must set size member; %s", fieldMetaInfo.getField().getName()));
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

            if(TypeEnum.ARRAY == fieldMetaInfo.getType()  || TypeEnum.LIST == fieldMetaInfo.getType()) {
                // 是否设置成员数量
                if(0 > fieldMetaInfo.getMagicField().size() && 0 > fieldMetaInfo.getMagicField().dynamicSizeFromOrder()) {
                    throw new MagicByteException(String.format("list and array member size must be set ; %s", fieldMetaInfo.getField().getName()));
                }

                // 检查目标字段是否先初始化
                if(0 < fieldMetaInfo.getMagicField().dynamicSizeFromOrder() && null == tmp.get(fieldMetaInfo.getMagicField().dynamicSizeFromOrder())) {
                    throw new MagicByteException(String.format("dynamicSizeFromOrder number should be less than target field order number; %s", fieldMetaInfo.getField().getName()));
                }

                // 检查目标字段是否为整数
                if(0 < fieldMetaInfo.getMagicField().dynamicSizeFromOrder() && (
                        TypeEnum.BYTE != tmp.get(fieldMetaInfo.getMagicField().dynamicSizeFromOrder()).getType() &&
                        TypeEnum.SHORT != tmp.get(fieldMetaInfo.getMagicField().dynamicSizeFromOrder()).getType() &&
                        TypeEnum.INT != tmp.get(fieldMetaInfo.getMagicField().dynamicSizeFromOrder()).getType() &&
                        TypeEnum.LONG != tmp.get(fieldMetaInfo.getMagicField().dynamicSizeFromOrder()).getType()
                )) {
                    throw new MagicByteException(String.format("dynamicSizeFromOrder target field should be number (int,short,byte,long); %s", fieldMetaInfo.getField().getName()));
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
            throw new MagicByteException(String.format("invalid byte array; do not converter to the java object : %s", classMetaInfo.getClazz().getName()));
        }
    }

    public static void assertFieldMetaInfoInitSuccess(boolean initRes, FieldMetaInfo fieldMetaInfo) {
        if(!initRes && fieldMetaInfo.getOwnerClazz().isStrict()) {
            throw new MagicByteException(String.format("can't parser field of class: %s.%s", fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
        }
    }
}
