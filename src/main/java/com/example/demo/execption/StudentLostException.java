package com.example.demo.execption;

public class StudentLostException extends StudentRuntimeException {
    public int code;
    public StudentLostException(String s,int code) {
        super(s);
        this.code = code;
    }
}
