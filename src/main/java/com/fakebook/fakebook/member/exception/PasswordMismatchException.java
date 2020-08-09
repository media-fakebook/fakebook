package com.fakebook.fakebook.member.exception;

public class PasswordMismatchException extends RuntimeException{
    private static final String message = "비밀번호를 확인해 주세요.";

    public PasswordMismatchException() {
        super(message);
    }
}
