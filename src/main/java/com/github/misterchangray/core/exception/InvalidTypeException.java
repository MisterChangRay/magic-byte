package com.github.misterchangray.core.exception;

public class InvalidTypeException extends MagicByteException {

    public InvalidTypeException(){ }

    public InvalidTypeException(String str){
        super(str);
    }
}
