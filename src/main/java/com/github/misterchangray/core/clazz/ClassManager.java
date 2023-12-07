package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.enums.TypeEnum;
import com.github.misterchangray.core.exception.InvalidParameterException;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 类注册管理器，这里负责数据模板注册管理
 * @description: class manager
 * @author: Ray.chang
 * @create: 2021-12-17 15:11
 **/
public class ClassManager {
    private static Map<Class<?>, ClassMetaInfo> cache = new HashMap<>();


    /**
     * 解析框架核心注解，包装为 <p>ClasMetaInfo</p>返回
     * 其中解析的大概流程为：
     *
     * @param clazz
     * @return
     */
    public static ClassMetaInfo getClassMetaInfo(Class<?> clazz) {
        ClassMetaInfo classMetaInfo = cache.get(clazz);
        if (null != classMetaInfo) {
            return classMetaInfo;
        }

        classMetaInfo = new ClassMetaInfo(clazz);
        classMetaInfo = parseClass(classMetaInfo);

        cache.put(clazz, classMetaInfo);
        return classMetaInfo;
    }

    private static ClassMetaInfo parseClass(ClassMetaInfo classMetaInfo) {
        ClassParser.getInstance().parse(classMetaInfo);
        afterLink(classMetaInfo);
        return classMetaInfo;
    }

    private static void afterLink(ClassMetaInfo classMetaInfo) {
        List<FieldMetaInfo>
                dynamicFields =  new ArrayList<>(),  // all dynamicSizeOf > 0 fields
                dynamicSizeFields =  new ArrayList<>(), // all dynamicSize = true fields
                calcLengthFields =  new ArrayList<>(), // all calcLength = true fields
                calcCheckCodeFields =  new ArrayList<>(), //  all checkCode = true fields
                cmdFields =  new ArrayList<>() //  all cmd = true fields
                        ;
        int suffixBytes = 0;

        for (int i = 0; i < classMetaInfo.getFlatFields().size(); i++) {
            FieldMetaInfo fieldMetaInfo = classMetaInfo.getFlatFields().get(i);
            if(dynamicSizeFields.size() > 0) {
                suffixBytes += fieldMetaInfo.getElementBytes() * fieldMetaInfo.getSize();
            }

            if(fieldMetaInfo.isCmdField()) {
                cmdFields.add(fieldMetaInfo);
            }
            if(fieldMetaInfo.isDynamic()) {
                dynamicFields.add(fieldMetaInfo);
            }
            if(fieldMetaInfo.isDynamicSize()) {
                dynamicSizeFields.add(fieldMetaInfo);
            }
            if(fieldMetaInfo.isCalcCheckCode()) {
                calcCheckCodeFields.add(fieldMetaInfo);
            }
            if(fieldMetaInfo.isCalcLength()) {
                calcLengthFields.add(fieldMetaInfo);
            }

            // 解析 dynamicSizeOf 引用持有
            fieldMetaInfo.linkDynamicSizeOf(classMetaInfo);

            // 当前为根节点时, 检查所有 dynamicSizeOf 引用持有是否正确
            if(Objects.isNull(classMetaInfo.getParent()) && fieldMetaInfo.isDynamic() && fieldMetaInfo.isDynamicSizeOf()) {
                if(Objects.isNull(fieldMetaInfo.getDynamicRef())) {
                    throw new InvalidParameterException("not found target field of dynamicSizeOf value; at: " + fieldMetaInfo.getFullName());
                }

                if(!fieldMetaInfo.getDynamicRef().getType().is(TypeEnum.BYTE, TypeEnum.SHORT, TypeEnum.INT,
                        TypeEnum.UBYTE, TypeEnum.USHORT, TypeEnum.UINT, TypeEnum.UNUMBER)) {
                    throw new InvalidParameterException("dynamic refs the type of filed only be (byte, short, int, UByte, UShort, UInt, UNumber); at: " + fieldMetaInfo.getFullName());
                }

            }
        }

        if(dynamicSizeFields.size() > 1) {
            throw new InvalidParameterException("dynamicSize only use once in the class; at: " + classMetaInfo.getFullName());
        } else if(dynamicSizeFields.size() == 1){
            dynamicSizeFields.get(0).setSuffixBytes(suffixBytes);
        }

        if(calcCheckCodeFields.size() > 1) {
            throw new InvalidParameterException("calcCheckCode only use once in the class; at: " + classMetaInfo.getFullName());
        }

        if(calcLengthFields.size() > 1) {
            throw new InvalidParameterException("calcLength only use once in the class; at: " + classMetaInfo.getFullName());
        }

        if(cmdFields.size() > 1) {
            throw new InvalidParameterException("cmd only use once in the class; at: " + classMetaInfo.getFullName());
        }

        if(dynamicFields.size() > 0 && dynamicSizeFields.size() > 0) {
            throw new InvalidParameterException("dynamicSize & dynamicSizeOf only use one in the class; at: " + classMetaInfo.getFullName());
        }
    }


    public static ClassMetaInfo getClassFieldMetaInfo(Class<?> clazz, ClassMetaInfo parent, FieldMetaInfo fieldMetaInfo) {
        ClassMetaInfo classMetaInfo = new ClassMetaInfo(clazz);
        classMetaInfo.setParent(parent);
        classMetaInfo.setOwnerField(fieldMetaInfo);

        parseClass(classMetaInfo);
        afterLink(classMetaInfo);
        return classMetaInfo;
    }
}
