package com.example.library.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends RuntimeException{
    private String code;
    public ValidationException(String code , String msg){
        super(msg);
        setCode(code);
    }
}