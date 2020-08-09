package com.fakebook.fakebook.member.exception;

public class DoesNotExistingUserIdException extends RuntimeException{
    private static final String message = "등록되지 않은 아이디입니다. id=";

    public DoesNotExistingUserIdException(String userId) {
        super(message+userId);
    }
}
