package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.Objects;

public class Packer {
    private static Packer packer;

    public static Packer getInstance() {
        if(null == packer) {
            packer = new Packer();
        }
        return packer;
    }


    public  <T> T packObject(DynamicByteBuffer data, Class<?> clazz, MagicChecker checker) throws MagicByteException {
        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(clazz);
        if(Objects.isNull(classMetaInfo)) {
            return null;
        }

        return doPackObject(data, classMetaInfo, checker);
    }



    public  <T> T doPackObject(DynamicByteBuffer data, ClassMetaInfo classMetaInfo, MagicChecker checker) throws MagicByteException {
        data.order(classMetaInfo.getByteOrder());
        T object = null;
        try {
            object = (T) classMetaInfo.getClazz().getDeclaredConstructor().newInstance();

            for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                encodeField(object, fieldMetaInfo, data, checker);
            }
        } catch (MagicParseException ae) {
            if(classMetaInfo.isStrict()) throw ae;
        } catch (IllegalAccessException ae) {
            AssertUtil.throwIllegalAccessException(classMetaInfo);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new MagicByteException("no public and no arguments constructor; at: " + classMetaInfo.getFullName());
        }
        return object;
    }


    private  void encodeField(Object object, FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data, MagicChecker checker) throws IllegalAccessException{
        if(fieldMetaInfo.getElementBytes() <= 0) return;


        Object val = null;
        try {
            val = fieldMetaInfo.getReader().readFormBuffer(data, object);

            verifyLength(fieldMetaInfo, data, val);
            verifyCheckCode(fieldMetaInfo, data, val, checker);

        } catch (UnsupportedEncodingException e) {
            throw new MagicByteException("not support charset of " + fieldMetaInfo.getCharset() + "; at :" + fieldMetaInfo.getFullName());
        }
        fieldMetaInfo.getWriter().writeToObject(object, val);
    }

    private void verifyLength(FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data, Object val) {
        if(!fieldMetaInfo.isCalcLength()) {
            return;
        }


        long actually = ConverterUtil.toNumber(fieldMetaInfo.getType(), val);
        long expect = data.capacity();
        if(actually != expect && fieldMetaInfo.getOwnerClazz().isStrict()) {
            byte[] array = data.array();
            throw new MagicParseException("the length isn't match, actually: " + actually + ", expect: " + expect + ", data:" + Base64.getEncoder().encodeToString(array));
        }
    }

    private void verifyCheckCode(FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data, Object val, MagicChecker checker) {
        if(checker == null) {
            return;
        }

        if(!fieldMetaInfo.isCalcCheckCode()) {
            return;
        }

        byte[] array = data.array();
        long actually = ConverterUtil.toNumber(fieldMetaInfo.getType(), val);
        long expect = checker.calcCheckCode(array);
        if(actually != expect && fieldMetaInfo.getOwnerClazz().isStrict()) {
            throw new MagicParseException("the checkCode isn't match, actually: " + actually + ", expect: " + expect + ", data:" + Base64.getEncoder().encodeToString(array));
        }
    }

}
