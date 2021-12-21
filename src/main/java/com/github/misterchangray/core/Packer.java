package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class Packer {
    private static Packer packer;

    public static Packer getInstance() {
        if(null == packer) {
            packer = new Packer();
        }
        return packer;
    }


    public  <T> T packObject(DynamicByteBuffer data, Class<?> clazz) throws MagicByteException {
        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(clazz);
        if(Objects.isNull(classMetaInfo)) {
            return null;
        }

        data.order(classMetaInfo.getByteOrder());
        T object = null;
        try {
            object = (T) clazz.getDeclaredConstructor().newInstance();

            for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                encodeField(object, fieldMetaInfo, data);
            }
        } catch (MagicParseException | IllegalAccessException ae) {
            AssertUtil.throwIllegalAccessException(classMetaInfo);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new MagicByteException("no public and no arguments constructor; at: " + classMetaInfo.getFullName());
        }
        return object;
    }


    private  void encodeField(Object object, FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data) throws IllegalAccessException{
        if(fieldMetaInfo.getElementBytes() <= 0) return;

        Object val = null;
        try {
            val = fieldMetaInfo.getReader().readFormBuffer(data, object);
        } catch (UnsupportedEncodingException e) {
            throw new MagicByteException("not support charset of " + fieldMetaInfo.getCharset() + "; at :" + fieldMetaInfo.getFullName());
        }
        fieldMetaInfo.getWriter().writeToObject(object, val);
    }

}
