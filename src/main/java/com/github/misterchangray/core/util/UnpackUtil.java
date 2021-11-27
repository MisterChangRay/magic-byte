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

        ByteBuffer res = ByteBuffer.allocate(classMetaInfo.getTotalBytes()).order(classMetaInfo.getByteOrder());
        for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
            res = res.put(encodeField(fieldMetaInfo, object, classMetaInfo.getByteOrder()).array());
        }

        return res;
    }


    /**
     * 反射获取实体类中的每个字段; 封装 byte 数组
     * @param fieldMetaInfo
     * @param object
     * @param byteOrder
     * @return
     */
    private static ByteBuffer encodeField(FieldMetaInfo fieldMetaInfo, Object object, ByteOrder byteOrder) {
        ByteBuffer res = ByteBuffer.allocate(fieldMetaInfo.getTotalBytes()).order(byteOrder);
        Object val = ClassUtil.readValue(object, fieldMetaInfo.getField());
        if(null == val) return res;

        List objectList = new ArrayList(20);
        switch (fieldMetaInfo.getType()) {
            case BYTE:
            case CHAR:
            case BOOLEAN:
            case SHORT:
            case INT:
            case FLOAT:
            case DOUBLE:
            case LONG:
                putBaseFieldValue(fieldMetaInfo.getType(), object, val ,  res);
                break;
            case STRING:
                byte[] bytes = new byte[0];
                try {
                    bytes = ((String) val).getBytes(fieldMetaInfo.getCharset());
                } catch (UnsupportedEncodingException e) {
                    throw new MagicByteException(String.format("UnsupportedEncoding; %s", fieldMetaInfo.getCharset()));
                }
                if(bytes.length > fieldMetaInfo.getTotalBytes()) {
                    bytes = Arrays.copyOf(bytes, fieldMetaInfo.getTotalBytes());
                }
                res.put(bytes);
                break;
            case ARRAY:
                for(int i = 0, length = Array.getLength(val); i<length; i++) {
                    if(i < length) objectList.add(Array.get(val, i));
                }
            case LIST:
                if(fieldMetaInfo.getType() == TypeEnum.LIST) objectList = (List) val;

                int index = 0;
                for (Iterator item = objectList.iterator(); item.hasNext() && index < fieldMetaInfo.getSize();) {
                    index ++;
                    Object temp = item.next();
                    if(null == temp) continue;

                    TypeEnum typeEnum = TypeEnum.getType(fieldMetaInfo.getClazz());
                    if(TypeEnum.OBJECT == typeEnum) {
                        res.put(unpackObject(temp).array());
                    } else {
                        putBaseFieldValue(typeEnum, object, temp, res);
                    }
                }
                break;
            case OBJECT:
                res.put(unpackObject(val).array());
                break;
        }
        return res;
    }

    private static void putBaseFieldValue(TypeEnum typeEnum,  Object object, Object value, ByteBuffer res) {
        if(null == value) return;;
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
