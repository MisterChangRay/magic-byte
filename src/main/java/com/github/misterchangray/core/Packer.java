package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.MessageManager;
import com.github.misterchangray.core.exception.InvalidCheckCodeException;
import com.github.misterchangray.core.exception.InvalidLengthException;
import com.github.misterchangray.core.exception.MagicByteException;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.ExceptionUtil;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * 反序列化/封包类
 *
 * 此类提供了一系列将字节数组转为对象的操作方法
 *
 */
public class Packer {
    private static Packer packer;

    public static Packer getInstance() {
        if(null == packer) {
            packer = new Packer();
        }
        return packer;
    }


    public  <T> T packObject(DynamicByteBuffer data, Class<?> clazz, MagicChecker checker) throws MagicByteException {
        if(Objects.isNull(clazz) && MessageManager.hasMessage()) {
            Class message = MessageManager.getMessage(data);
            if(Objects.isNull(message)) {
                return null;
            }
            clazz = message;
        }

        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(clazz);
        if(Objects.isNull(classMetaInfo)) {
            return null;
        }

        return doPackObject(data, classMetaInfo, checker, null);
    }



    public  <T> T doPackObject(DynamicByteBuffer data, ClassMetaInfo classMetaInfo, MagicChecker checker, Object root) throws MagicByteException {
        data.order(classMetaInfo.getByteOrder());
        T object = null;

        try {
            if(Objects.nonNull(classMetaInfo.getCustomConverter())) {
                 object = doCustomPackObject(data, classMetaInfo);
                return object;
            }

            object = (T) classMetaInfo.getClazz().getDeclaredConstructor().newInstance();
            if(Objects.isNull(root)) {
                root = object;
            }
            for (FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                encodeField(object, fieldMetaInfo, data, checker, root);
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


    private  void encodeField(Object object, FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data, MagicChecker checker, Object root) throws IllegalAccessException{
        if(fieldMetaInfo.getElementBytes() <= 0 && Objects.isNull(fieldMetaInfo.getCustomConverter())) return;


        Object val = null;
        val = fieldMetaInfo.getReader().readFormBuffer(data, object);
        fieldMetaInfo.getWriter().writeToObject(object, val);

        verifyLength(fieldMetaInfo, data, val, root);
        verifyCheckCode(fieldMetaInfo, data, val, checker, root);

    }

    private void verifyLength(FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data, Object val, Object root) {
        if(!fieldMetaInfo.isCalcLength()) {
            return;
        }


        long actually = ConverterUtil.toNumber(fieldMetaInfo.getType(), (Number) val);
        long expect = data.capacity();
        if(actually != expect) {
            byte[] array = data.array();
            throw new InvalidLengthException(root,
                    "the length isn't match, actually: " + actually + ", expect: " + expect + ", data:" + Base64.getEncoder().encodeToString(array));
        }
    }

    private void verifyCheckCode(FieldMetaInfo fieldMetaInfo, DynamicByteBuffer data, Object val, MagicChecker checker, Object root) {
        if(checker == null) {
            return;
        }

        if(!fieldMetaInfo.isCalcCheckCode()) {
            return;
        }

        boolean checkerPass = true;
        byte[] array = data.array();
        byte[] expect = checker.calcCheckCode(array);
        if(Objects.isNull(expect) || expect.length == 0) {
            throw new InvalidCheckCodeException(root,
                    "calcCheckCode return null! data:" + Base64.getEncoder().encodeToString(array));
        }

        int bytes = fieldMetaInfo.getSize() * fieldMetaInfo.getElementBytes();
        if(expect.length > bytes) {
            expect = Arrays.copyOfRange(expect,0, bytes);
        }
        for (int i = data.position() - 1, j = expect.length - 1; j > -1; i--, j--) {
            if(array[i] != expect[j]) {
                checkerPass = false;
                break;
            }
        }

        if(!checkerPass) {
            throw new InvalidCheckCodeException(root,
                    "the checkCode isn't match, data:" + Base64.getEncoder().encodeToString(array));
        }
    }

}
