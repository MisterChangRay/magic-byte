package com.github.misterchangray.core.exception;

public class InvalidCheckCodeException extends MagicByteException {

    public InvalidCheckCodeException(Object data, String msg){
        super(data, msg);
    }


}
