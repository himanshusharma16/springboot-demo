package com.example.demo.execption;

public class StudentGenerateException extends StudentRuntimeException {
    public static final int code = 401;
    public StudentGenerateException(String s) {
        super(s);
    }
}
