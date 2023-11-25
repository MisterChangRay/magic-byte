package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.ExceptionUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;
import com.github.misterchangray.core.util.OGNLUtil;

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

        try {
            res.delayCalc(checker);
        } catch (IllegalAccessException e) {
            ExceptionUtil.throwIllegalAccessException(classMetaInfo.getClazz());
        }

        return res;
    }

    public  <T> DynamicByteBuffer unpackObject(DynamicByteBuffer res, T object, ClassMetaInfo classMetaInfo) {
        if(Objects.isNull(classMetaInfo)) return null;
        try {
            if(Objects.nonNull(classMetaInfo.getCustomConverter())) {
                doCustomUnPackObject(res, object, classMetaInfo);
                return res;
            }

            for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                decodeField(fieldMetaInfo, object,  res);
            }

        } catch (MagicParseException ae) {
            if(classMetaInfo.isStrict()) throw ae;
        } catch (IllegalAccessException ae) {
            ExceptionUtil.throwIllegalAccessException(classMetaInfo.getClazz());
        }
        return res;
    }

    private <T> void doCustomUnPackObject(DynamicByteBuffer res, T object, ClassMetaInfo classMetaInfo) throws IllegalAccessException {
        classMetaInfo.getWriter().writeToBuffer(res, object, null);
    }


    /**
     * 反射获取实体类中的每个字段; 封装 byte 数组
     * @param fieldMetaInfo
     * @param object
     * @param res
     * @return
     */
    private  void decodeField(FieldMetaInfo fieldMetaInfo, Object object, DynamicByteBuffer res) throws IllegalAccessException {
        if(fieldMetaInfo.isCalcLength()) res.setLengthFieldWrapper(fieldMetaInfo);
        if(fieldMetaInfo.isCalcCheckCode()) res.setCheckCodeFieldWrapper(fieldMetaInfo);

        Object val = fieldMetaInfo.getReader().readFormObject(object);

        if(Objects.nonNull(fieldMetaInfo.getOgnl())) {
            val = OGNLUtil.eval(object, 2, fieldMetaInfo.getOgnl());
        }
        fieldMetaInfo.getWriter().writeToBuffer(res, val, object);
    }

}
