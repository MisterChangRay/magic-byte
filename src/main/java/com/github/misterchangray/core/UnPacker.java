package com.github.misterchangray.core;

import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.enums.ByteOrder;
import com.github.misterchangray.core.exception.MagicParseException;
import com.github.misterchangray.core.util.DynamicByteBuffer;
import com.github.misterchangray.core.util.ExceptionUtil;

import java.util.Objects;

/**
 * 序列化类
 *
 * 此类提供了对象转化为字节数组的操作方法
 *
 */
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
        res.setPackObj(object);
        this.unpackObject(res, object, classMetaInfo, object);

        try {
            res.delayCalc(checker);
        } catch (IllegalAccessException e) {
            ExceptionUtil.throwIllegalAccessException(classMetaInfo.getClazz());
        }

        return res;
    }

    public  <T> DynamicByteBuffer unpackObject(DynamicByteBuffer res, T object, ClassMetaInfo classMetaInfo, Object root) {
        if(Objects.isNull(classMetaInfo)) return null;
        try {
            if(Objects.nonNull(classMetaInfo.getCustomConverter())) {
                doCustomUnPackObject(res, object, classMetaInfo);
                return res;
            }

            for(FieldMetaInfo fieldMetaInfo : classMetaInfo.getFields()) {
                // 如果对属性额外配置了端序则以属性使用的端序为准，否则以全局/类配置的为准
                if (fieldMetaInfo.getByteOrder() == ByteOrder.AUTO) {
                    res.order(classMetaInfo.getByteOrder());
                } else {
                    res.order(fieldMetaInfo.getByteOrder().getBytes());
                }

                decodeField(fieldMetaInfo, object,  res, root);
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
    private  void decodeField(FieldMetaInfo fieldMetaInfo, Object object, DynamicByteBuffer res, Object root) throws IllegalAccessException {
        if(fieldMetaInfo.isCalcLength()) {
            res.setLengthFieldWrapper(fieldMetaInfo);
        }
        if(fieldMetaInfo.isCalcCheckCode()) res.setCheckCodeFieldWrapper(fieldMetaInfo);

        Object val = fieldMetaInfo.getReader().readFormObject(object);

        fieldMetaInfo.getWriter().writeToBuffer(res, val, object);
    }

}
