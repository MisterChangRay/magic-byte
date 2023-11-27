package com.github.misterchangray.core.exception;


import java.util.Objects;

public class MagicByteException extends RuntimeException {
    private Object data;

    public MagicByteException(Object data, String msg){
        super(msg);
        this.data = data;
    }

    public MagicByteException(String str){
        super(str);
    }

    public Object getData() {
        return data;
    }
}
