package com.example.library.exception;


public class BookNotFoundException extends RuntimeException{
    private String code;
    public BookNotFoundException(String code , String msg){
        super(msg);
        setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}