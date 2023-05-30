package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.exception.InvalidCheckCodeException;
import com.github.misterchangray.core.exception.InvalidLengthException;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.ExceptionUtil;
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
            if(Objects.nonNull(classMetaInfo.getCustomConverter())) {
                 object = doCustomPackObject(data, classMetaInfo);
                return object;
            }

            object = (T) classMetaInfo.getClazz().getDeclaredConstructor().newInstance();
            for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                encodeField(object, fieldMetaInfo, data, checker);
            }
        } catch (MagicParseException ae) {
            if(classMetaInfo.isStrict()) throw ae;
        } catch (IllegalAccessException ae) {
            ExceptionUtil.throwIllegalAccessException(classMetaInfo.getClazz());
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            ExceptionUtil.throwInstanceErrorException(classMetaInfo.getClazz());
        }
        return object;
    }

    private <T> T doCustomPackObject(DynamicByteBuffer data, ClassMetaInfo classMetaInfo) throws IllegalAccessException {
        return  (T)  classMetaInfo.getReader().readFormBuffer(data, null);
    }


    private  void encodeField(Object object, FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data, MagicChecker checker) throws IllegalAccessException{
        if(fieldMetaInfo.getElementBytes() <= 0 && Objects.isNull(fieldMetaInfo.getCustomConverter())) return;


        Object val = null;
        val = fieldMetaInfo.getReader().readFormBuffer(data, object);

        verifyLength(fieldMetaInfo, data, val);
        verifyCheckCode(fieldMetaInfo, data, val, checker);
        fieldMetaInfo.getWriter().writeToObject(object, val);
    }

    private void verifyLength(FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data, Object val) {
        if(!fieldMetaInfo.isCalcLength()) {
            return;
        }


        long actually = ConverterUtil.toNumber(fieldMetaInfo.getType(), (Number) val);
        long expect = data.capacity();
        if(actually != expect && fieldMetaInfo.getOwnerClazz().isStrict()) {
            byte[] array = data.array();
            throw new InvalidLengthException("the length isn't match, actually: " + actually + ", expect: " + expect + ", data:" + Base64.getEncoder().encodeToString(array));
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
        long actually = ConverterUtil.toNumber(fieldMetaInfo.getType(), (Number) val);
        long expect = ConverterUtil.toNumber(fieldMetaInfo.getType(), checker.calcCheckCode(array));
        if(actually != expect && fieldMetaInfo.getOwnerClazz().isStrict()) {
            throw new InvalidCheckCodeException("the checkCode isn't match, actually: " + actually + ", expect: " + expect + ", data:" + Base64.getEncoder().encodeToString(array));
        }
    }

}
