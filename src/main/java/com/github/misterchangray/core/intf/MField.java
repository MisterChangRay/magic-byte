package com.github.misterchangray.core.intf;

/**
 * @description: 字段操作
 * @author: Ray.chang
 * @create: 2021-12-19 12:42
 **/
public interface MField {
    MReader getReader();
    MWriter getWriter();
    void setWriter(MWriter writer);

    void setReader(MReader reader);

}
