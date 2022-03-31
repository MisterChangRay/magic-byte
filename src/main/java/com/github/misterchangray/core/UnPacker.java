package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.AssertUtil;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

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

        this.unpackObject(res, object, classMetaInfo);

        this.unpackDelayFields(object, classMetaInfo, res, checker);
        return res;
    }

    private <T> void unpackDelayFields(T object, ClassMetaInfo classMetaInfo, DynamicByteBuffer res, MagicChecker checker) {
        try {
            for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFlatFields()) {
                if(!fieldMetaInfo.isCalcCheckCode() && !fieldMetaInfo.isCalcLength()) continue;

                doDelayCalcField(fieldMetaInfo, object,  res, checker);
            }
        } catch (MagicParseException ae) {
            if(classMetaInfo.isStrict()) throw ae;
        } catch (IllegalAccessException ae) {
            AssertUtil.throwIllegalAccessException(classMetaInfo);
        } catch (Exception ae) {
            throw  ae;
        }
    }

    /**
     * 反射获取实体类中的每个字段; 封装 byte 数组
     * @param fieldMetaInfo
     * @param object
     * @param res
     * @return
     */
    private  void doDelayCalcField(FieldMetaInfo fieldMetaInfo, Object object, DynamicByteBuffer res, MagicChecker checker) throws IllegalAccessException {
        long val = 0;
        int offset = 0;
        if(fieldMetaInfo.isCalcLength()) {
            val = res.position();
            offset = res.getLengthOffset();
        }

        if(fieldMetaInfo.isCalcCheckCode() && Objects.nonNull(checker)) {
            val = checker.calcCheckCode(res.array());
            offset = res.getCheckCodeOffset();
        }

        fieldMetaInfo.getWriter().writeToBuffer(res, ConverterUtil.toTargetObject(fieldMetaInfo.getType(), val), object, offset);
    }


    public  <T> DynamicByteBuffer unpackObject(DynamicByteBuffer res, T object, ClassMetaInfo classMetaInfo) {
        if(Objects.isNull(classMetaInfo)) return null;

        try {
            for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                decodeField(fieldMetaInfo, object,  res);
            }
        } catch (MagicParseException ae) {
            if(classMetaInfo.isStrict()) throw ae;
        } catch (IllegalAccessException ae) {
            AssertUtil.throwIllegalAccessException(classMetaInfo);
        }
        return res;
    }


    /**
     * 反射获取实体类中的每个字段; 封装 byte 数组
     * @param fieldMetaInfo
     * @param object
     * @param res
     * @return
     */
    private  void decodeField(FieldMetaInfo fieldMetaInfo, Object object, DynamicByteBuffer res) throws IllegalAccessException {
        res.setCheckCodeOffset(fieldMetaInfo);
        res.setLengthOffset(fieldMetaInfo);

        Object val = fieldMetaInfo.getReader().readFormObject(object);
        fieldMetaInfo.getWriter().writeToBuffer(res, val, object);
    }

}
