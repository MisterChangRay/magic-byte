package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.intf.MConverter;

public class CustomConverterInfo {

    /**
     * 附加参数
     * @return
     */
    private String attachParams;


    /**
     * 自定义序列化转换器
     */
    private MConverter converter;


    /**
     * 固定长度
     * @return
     */
    private int fixSize ;



    public boolean isFixsize() {
        return this.fixSize >= 0;
    }


    public MConverter getConverter() {
        return converter;
    }

    public void setConverter(MConverter converter) {
        this.converter = converter;
    }

    public int getFixSize() {
        return fixSize;
    }

    public void setFixSize(int fixSize) {
        this.fixSize = fixSize;
    }

    public String getAttachParams() {
        return attachParams;
    }

    public void setAttachParams(String attachParams) {
        this.attachParams = attachParams;
    }

    public CustomConverterInfo(String attachParams, MConverter converter, int fixSize) {
        this.attachParams = attachParams;
        this.converter = converter;
        this.fixSize = fixSize;
    }
}
