package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class UnPacker {

    private static UnPacker unPacker;

    public static UnPacker getInstance() {
        if(null == unPacker) {
            unPacker = new UnPacker();
        }
        return unPacker;
    }

    /**
     * 类编码器;
     * 按照格式将类组装为字节数组
     * @param object
     * @param <T>
     * @return
     */
    public  <T> byte[] unpackObject(T object) {
        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(object.getClass());
        DynamicByteBuffer res = null;
        if(classMetaInfo.isDynamic()) {
            res = DynamicByteBuffer.allocate().order(classMetaInfo.getByteOrder());
        } else {
            res = DynamicByteBuffer.allocate(classMetaInfo.getElementBytes()).order(classMetaInfo.getByteOrder());
        }

        this.unpackObject(res, object, classMetaInfo);
        return res.array();
    }

    public  <T> DynamicByteBuffer unpackObject(DynamicByteBuffer res, T object, ClassMetaInfo classMetaInfo) {
        if(Objects.isNull(classMetaInfo)) return null;

        try {
            for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                decodeField(fieldMetaInfo, object,  res);
            }
        } catch (MagicParseException | IllegalAccessException ae) {
            AssertUtil.throwIllegalAccessException(classMetaInfo);
        }
        return res;
    }


    /**
     * 反射获取实体类中的每个字段; 封装 byte 数组
     * @param fieldMetaInfo
     * @param object
     * @param byteOrder
     * @param dynamicSize
     * @return
     */
    private  void decodeField(FieldMetaInfo fieldMetaInfo, Object object, DynamicByteBuffer res) throws IllegalAccessException {
        Object val = fieldMetaInfo.getReader().readFormObject(object);
        fieldMetaInfo.getWriter().writeToBuffer(res, val, object);
    }
//
//    private  void doEncodingContainer(FieldMetaInfo fieldMetaInfo, List objectList, DynamicByteBuffer res, int size) {
//        AssertUtil.assertLengthEqualsDeclare(fieldMetaInfo, objectList, size);
//
//        int index = 0;
//        for (Iterator item = objectList.iterator(); item.hasNext() && index < size;) {
//            index ++;
//            Object temp = item.next();
//            if(null == temp) {
//                res.put(new byte[fieldMetaInfo.getElementBytes()]);
//                continue;
//            }
//
//            TypeEnum typeEnum = TypeEnum.getType(fieldMetaInfo.getClazz());
//            if(TypeEnum.OBJECT == typeEnum) {
//                res.put(unpackObject(temp));
//            } else {
//                putBaseFieldValue(typeEnum,  temp, res);
//            }
//        }
//    }
//
//    private  void fill(ByteBuffer res) {
//        for (int i = 0; i < res.capacity(); i++) {
//            res.put((byte) 0);
//        }
//    }
//
//    private  void putBaseFieldValue(TypeEnum typeEnum,   Object value, DynamicByteBuffer res) {
//        switch (typeEnum) {
//            case BOOLEAN:
//                value = (boolean)value ? 1 : 0;
//                res.put(((Integer) value).byteValue());
//                break;
//            case BYTE:
//                res.put((byte) value);
//                break;
//            case CHAR:
//                Character character = (Character) value;
//                res.putShort((short) character.charValue());
//                break;
//            case SHORT:
//                res.putShort((short) value);
//                break;
//            case INT:
//                res.putInt((int) value);
//                break;
//            case FLOAT:
//                res.putFloat((float) value);
//                break;
//            case DOUBLE:
//                res.putDouble((double) value);
//                break;
//            case LONG:
//                res.putLong((long) value);
//                break;
//        }
//    }


}
