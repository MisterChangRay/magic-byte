package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.intf.MConverter;

public class CustomConverterInfo {

    /**
     * 附加参数
     */
    private String[] attachParams;


    /**
     * 自定义序列化转换器
     */
    private MConverter<?> converter;


    /**
     * 固定长度
     */
    private int fixSize ;

    private boolean handleCollection;


    public boolean isHandleCollection() {
        return handleCollection;
    }

    public void setHandleCollection(boolean handleCollection) {
        this.handleCollection = handleCollection;
    }

    public boolean isFixSize() {
        return this.fixSize >= 0;
    }

    // todo 这里如果加上<?> 会导致调用的方法无法使用 Object 作为入参对象，后续需要解决
    public MConverter getConverter() {
        return converter;
    }

    public void setConverter(MConverter<?> converter) {
        this.converter = converter;
    }

    public int getFixSize() {
        return fixSize;
    }

    public void setFixSize(int fixSize) {
        this.fixSize = fixSize;
    }

    public String[] getAttachParams() {
        return attachParams;
    }

    public void setAttachParams(String[] attachParams) {
        this.attachParams = attachParams;
    }

    public CustomConverterInfo(String[] attachParams, MConverter<?> converter, int fixSize, boolean handleCollection) {
        this.attachParams = attachParams;
        this.converter = converter;
        this.fixSize = fixSize;
        this.handleCollection = handleCollection;
    }
}
