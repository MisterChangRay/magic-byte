package com.github.misterchangray.core.util;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.metainfo.ClassMetaInfo;
import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class UnpackUtil {


    /**
     * 类编码器;
     * 按照格式将类组装为字节数组
     * @param object
     * @param <T>
     * @return
     */
    public static <T> byte[] unpackObject(T object)  {
        ClassMetaInfo classMetaInfo = ClassMetaInfoUtil.buildClassMetaInfo(object.getClass());
        if(Objects.isNull(classMetaInfo)) return null;

        DynamicByteBuffer res = null;
        if(classMetaInfo.isDynamic()) {
            preInitObject(classMetaInfo, object);
            res = DynamicByteBuffer.allocate().order(classMetaInfo.getByteOrder());
        } else {
            res = DynamicByteBuffer.allocate(classMetaInfo.getElementBytes()).order(classMetaInfo.getByteOrder());
        }

        for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            encodeField(fieldMetaInfo, object, classMetaInfo.getByteOrder(), res);
        }

        return res.array();
    }

    private static <T> void preInitObject(ClassMetaInfo classMetaInfo, T object) {
        for (FieldMetaInfo field : classMetaInfo.getFields()) {
            if(!field.isDynamic()) continue;
            if(field.getType() != TypeEnum.LIST &&
                    field.getType() != TypeEnum.ARRAY &&
                    field.getType() != TypeEnum.STRING) {
                continue;
            }

            field.calcDynamicSize(object, ClassUtil.readValue(object, field.getField()));
        }

    }


    /**
     * 反射获取实体类中的每个字段; 封装 byte 数组
     * @param fieldMetaInfo
     * @param object
     * @param byteOrder
     * @param dynamicSize
     * @return
     */
    private static void encodeField(FieldMetaInfo fieldMetaInfo, Object object, ByteOrder byteOrder, DynamicByteBuffer res) {
        if(Objects.isNull(object)) return;

        Object val = ClassUtil.readValue(object, fieldMetaInfo.getField());
        List objectList = new ArrayList(20);
        switch (fieldMetaInfo.getType()) {
            case BOOLEAN:
                if(Objects.isNull(val)) val = false;
            case BYTE:
                if(Objects.isNull(val)) val = (byte)0;
            case CHAR:
                if(Objects.isNull(val)) val = (char)0;
            case SHORT:
                if(Objects.isNull(val)) val = (short)0;
            case FLOAT:
                if(Objects.isNull(val)) val = (float)0;
            case DOUBLE:
                if(Objects.isNull(val)) val = (double)0;
            case LONG:
                if(Objects.isNull(val)) val = (long)0;
            case INT:
                if(Objects.isNull(val)) val = 0;
                putBaseFieldValue(fieldMetaInfo.getType(), val ,  res);
                break;
            case STRING:
                byte[] bytes = new byte[0];
                try {
                    bytes = ((String) val).getBytes(fieldMetaInfo.getCharset());
                } catch (UnsupportedEncodingException e) {}
                Integer size = fieldMetaInfo.calcDynamicSize(object, val);
                bytes = Arrays.copyOf(bytes, size);
                res.put(bytes);
                break;
            case ARRAY: {
                Integer len = fieldMetaInfo.calcDynamicSize(object, val);
                int totalBytes = len * fieldMetaInfo.getElementBytes();
                bytes = new byte[totalBytes];

                if (Objects.isNull(val)) {
                    res.put(bytes);
                    break;
                }
                for (int i = 0, length = Array.getLength(val); i < length; i++) {
                    objectList.add(Array.get(val, i));
                }
                doEncodingContainer(fieldMetaInfo, objectList, res, len);
            }
                break;
            case LIST: {
                objectList = (List) val;
                Integer len = fieldMetaInfo.calcDynamicSize(object, val);
                int totalBytes = len * fieldMetaInfo.getElementBytes();
                bytes = new byte[totalBytes];

                if (Objects.isNull(val)) {
                    res.put(bytes);
                    break;
                }
                doEncodingContainer(fieldMetaInfo, objectList, res, len);
            }
                break;
            case OBJECT:
                if(Objects.isNull(val)) {
                    res.put(new byte[fieldMetaInfo.getElementBytes()]);
                } else {
                    res.put(unpackObject(val));
                }
                break;
        }
    }

    private static void doEncodingContainer(FieldMetaInfo fieldMetaInfo, List objectList, DynamicByteBuffer res, int size) {
        AssertUtil.assertLengthEqualsDeclare(fieldMetaInfo, objectList, size);

        int index = 0;
        for (Iterator item = objectList.iterator(); item.hasNext() && index < size;) {
            index ++;
            Object temp = item.next();
            if(null == temp) {
                res.put(new byte[fieldMetaInfo.getElementBytes()]);
                continue;
            }

            TypeEnum typeEnum = TypeEnum.getType(fieldMetaInfo.getClazz());
            if(TypeEnum.OBJECT == typeEnum) {
                res.put(unpackObject(temp));
            } else {
                putBaseFieldValue(typeEnum,  temp, res);
            }
        }
    }

    private static void fill(ByteBuffer res) {
        for (int i = 0; i < res.capacity(); i++) {
            res.put((byte) 0);
        }
    }

    private static void putBaseFieldValue(TypeEnum typeEnum,   Object value, DynamicByteBuffer res) {
        switch (typeEnum) {
            case BOOLEAN:
                value = (boolean)value ? 1 : 0;
                res.put(((Integer) value).byteValue());
                break;
            case BYTE:
                res.put((byte) value);
                break;
            case CHAR:
                Character character = (Character) value;
                res.putShort((short) character.charValue());
                break;
            case SHORT:
                res.putShort((short) value);
                break;
            case INT:
                res.putInt((int) value);
                break;
            case FLOAT:
                res.putFloat((float) value);
                break;
            case DOUBLE:
                res.putDouble((double) value);
                break;
            case LONG:
                res.putLong((long) value);
                break;
        }
    }


}
