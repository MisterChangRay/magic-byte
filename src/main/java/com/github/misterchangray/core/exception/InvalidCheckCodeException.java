package com.github.misterchangray.core.exception;

public class InvalidCheckCodeException extends MagicByteException {

    public InvalidCheckCodeException(){ }

    public InvalidCheckCodeException(String str){
        super(str);
    }
}
