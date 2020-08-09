package com.fakebook.fakebook.member.exception;

import org.springframework.web.util.NestedServletException;

public class DuplicatedUserIdException extends NestedServletException {
    private static final String message = "해당 ID로 가입된 계정이 이미 존재합니다. id=";

    public DuplicatedUserIdException(String userId) {
        super(message+userId);
    }
}
