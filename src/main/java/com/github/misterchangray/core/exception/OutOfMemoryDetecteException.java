package com.github.misterchangray.core.exception;

public class OutOfMemoryDetecteException extends MagicByteException {

    public OutOfMemoryDetecteException(){ }

    public OutOfMemoryDetecteException(String str){
        super(str);
    }
}
