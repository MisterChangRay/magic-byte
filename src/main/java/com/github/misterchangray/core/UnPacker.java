package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfoWrapper;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * @param <T>
     * @param object
     * @param checker
     * @return
     */
    public  <T> DynamicByteBuffer unpackObject(T object, MagicChecker checker) {
        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(object.getClass());
        DynamicByteBuffer res = null;
        if(classMetaInfo.isDynamic()) {
            res = DynamicByteBuffer.allocate().order(classMetaInfo.getByteOrder());
        } else {
            res = DynamicByteBuffer.allocate(classMetaInfo.getElementBytes()).order(classMetaInfo.getByteOrder());
        }

        this.unpackObject(res, object, classMetaInfo, checker);
        return res;
    }

    public  <T> DynamicByteBuffer unpackObject(DynamicByteBuffer res, T object, ClassMetaInfo classMetaInfo, MagicChecker checker) {
        if(Objects.isNull(classMetaInfo)) return null;

        List<FieldMetaInfoWrapper> delayFieldMetaInfoWrappers = new ArrayList<>(10);
        try {
            for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                if(fieldMetaInfo.isCalcCheckCode() || fieldMetaInfo.isCalcLength()) {
                    delayFieldMetaInfoWrappers.add(new FieldMetaInfoWrapper(fieldMetaInfo, res.position()));
                }
                decodeField(fieldMetaInfo, object,  res);
            }

            if(delayFieldMetaInfoWrappers.size() > 0) {
                for(FieldMetaInfoWrapper fieldMetaInfoWrapper : delayFieldMetaInfoWrappers) {
                    decodeField(fieldMetaInfoWrapper, object,  res, checker);
                }
            }
        } catch (MagicParseException ae) {
            if(classMetaInfo.isStrict()) throw ae;
        } catch (IllegalAccessException ae) {
            AssertUtil.throwIllegalAccessException(classMetaInfo);
        }
        return res;
    }

    private  void decodeField(FieldMetaInfoWrapper fieldMetaInfoWrapper, Object object, DynamicByteBuffer res, MagicChecker checker) throws IllegalAccessException {
        FieldMetaInfo fieldMetaInfo = fieldMetaInfoWrapper.getFieldMetaInfo();

        long val = 0;
        if(fieldMetaInfo.isCalcLength()) {
            val = res.position();
        }
        if(fieldMetaInfo.isCalcCheckCode() && Objects.nonNull(checker)) {
            val = checker.calcCheckCode(res.array());
        }
        fieldMetaInfo.getWriter().writeToBuffer(res, val, object);
    }

    /**
     * 反射获取实体类中的每个字段; 封装 byte 数组
     * @param fieldMetaInfo
     * @param object
     * @param res
     * @return
     */
    private  void decodeField(FieldMetaInfo fieldMetaInfo, Object object, DynamicByteBuffer res) throws IllegalAccessException {
        Object val = fieldMetaInfo.getReader().readFormObject(object);
        fieldMetaInfo.getWriter().writeToBuffer(res, val, object);
    }

}
