package com.github.misterchangray.core.intf;

import com.github.misterchangray.core.clazz.FieldMetaInfo;

import java.util.List;

/**
 * @description: 类操作
 * @author: Ray.chang
 * @create: 2021-12-19 12:42
 **/
public interface MClass {
    List<FieldMetaInfo> getFields();

    FieldMetaInfo getFieldMetaInfoByOrderId(int orderId);
}
