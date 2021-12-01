package com.github.misterchangray.core.util;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.MagicByteException;
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
    public static <T> ByteBuffer unpackObject(T object)  {
        ClassMetaInfo classMetaInfo = ClassMetaInfoUtil.buildClassMetaInfo(object.getClass());
        if(Objects.isNull(classMetaInfo)) return null;

        int[] dynamicSize = Arrays.copyOf(classMetaInfo.getDynamicSize(), classMetaInfo.getDynamicSize().length);
        int totalBytes = classMetaInfo.getTotalBytes();
        if(classMetaInfo.isDynamic()) {
            totalBytes = calcTotalBytes(classMetaInfo, dynamicSize, object);
        }

        ByteBuffer res = ByteBuffer.allocate(totalBytes).order(classMetaInfo.getByteOrder());
        for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            res = res.put(encodeField(fieldMetaInfo, object, classMetaInfo.getByteOrder(), dynamicSize).array());
        }

        return res;
    }

    private static <T> int calcTotalBytes(ClassMetaInfo classMetaInfo, int[] dynamicSize, T object) {
        int res = 0;
        for (FieldMetaInfo field : classMetaInfo.getFields()) {
            if(field.isDynamic()){
                switch (field.getType()) {
                    case LIST:
                    case ARRAY:
                        Object o = ClassUtil.readValue(object, field.getField());
                        dynamicSize[field.getOrderId()] = CollectionUtil.sizeOfCollection(field, o) * field.getElementBytes();
                        break;
                    case STRING:
                        String s = (String) ClassUtil.readValue(object, field.getField());
                        dynamicSize[field.getOrderId()] = s.length();
                        break;
                }
                res += dynamicSize[field.getOrderId()];
            } else {
                res += field.getTotalBytes();
            }
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
    private static ByteBuffer encodeField(FieldMetaInfo fieldMetaInfo, Object object, ByteOrder byteOrder, int[] dynamicSize) {
        ByteBuffer res = ByteBuffer.allocate(dynamicSize[fieldMetaInfo.getOrderId()]).order(byteOrder);
        Object val = ClassUtil.readValue(object, fieldMetaInfo.getField());

        if(fieldMetaInfo.isDynamic() && Objects.isNull(val)) {
            throw new MagicByteException(String.format("dynamic field not be null: %s.%s", fieldMetaInfo.getOwnerClazz().getClazz().getName(), fieldMetaInfo.getField().getName()));
        }

        List objectList = new ArrayList(20);
        switch (fieldMetaInfo.getType()) {
            case BOOLEAN:
                if(Objects.isNull(val)) val = false;
            case BYTE:
            case CHAR:
            case SHORT:
            case INT:
            case FLOAT:
            case DOUBLE:
            case LONG:
                if(Objects.isNull(val)) val = 0;
                putBaseFieldValue(fieldMetaInfo.getType(), object, val ,  res);
                break;
            case STRING:
                byte[] bytes = new byte[0];
                try {
                    bytes = ((String) val).getBytes(fieldMetaInfo.getCharset());
                } catch (UnsupportedEncodingException e) {
                    throw new MagicByteException(String.format("UnsupportedEncoding; %s", fieldMetaInfo.getCharset()));
                }
                if(bytes.length > dynamicSize[fieldMetaInfo.getOrderId()]){
                    bytes = Arrays.copyOf(bytes, dynamicSize[fieldMetaInfo.getOrderId()]);
                }
                res.put(bytes);
                break;
            case ARRAY:
                if(Objects.isNull(val)) {
                    fill(res);
                    break;
                }
                for(int i = 0, length = Array.getLength(val); i<length; i++) {
                    if(i < length) objectList.add(Array.get(val, i));
                }
            case LIST:
                if(fieldMetaInfo.getType() == TypeEnum.LIST) objectList = (List) val;

                int index = 0;
                int size = fieldMetaInfo.getSize();
                if(fieldMetaInfo.isDynamic()) {
                    size = dynamicSize[fieldMetaInfo.getOrderId()] / fieldMetaInfo.getElementBytes();
                }
                for (Iterator item = objectList.iterator(); item.hasNext() && index < size;) {
                    index ++;
                    Object temp = item.next();
                    if(null == temp) {
                        res.put(new byte[fieldMetaInfo.getElementBytes()]);
                        continue;
                    }

                    TypeEnum typeEnum = TypeEnum.getType(fieldMetaInfo.getClazz());
                    if(TypeEnum.OBJECT == typeEnum) {
                        res.put(unpackObject(temp).array());
                    } else {
                        putBaseFieldValue(typeEnum, object, temp, res);
                    }
                }
                break;
            case OBJECT:
                if(Objects.isNull(val)) {
                    res.put(new byte[fieldMetaInfo.getTotalBytes()]);
                } else {
                    res.put(unpackObject(val).array());
                }
                break;
        }
        return res;
    }

    private static void fill(ByteBuffer res) {
        for (int i = 0; i < res.capacity(); i++) {
            res.put((byte) 0);
        }
    }

    private static void putBaseFieldValue(TypeEnum typeEnum,  Object object, Object value, ByteBuffer res) {
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
