package com.github.misterchangray.core.exception;

public class InvalidLengthException extends MagicByteException {

    public InvalidLengthException(){ }

    public InvalidLengthException(String str){
        super(str);
    }
}
