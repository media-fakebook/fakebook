package com.fakebook.fakebook.post.exception;

public class IllegalAccessToPostException extends RuntimeException {
    private static final String message = "자신이 작성한 게시물만 수정 및 삭제할 수 있습니다.";

    public IllegalAccessToPostException() {
        super(message);
    }
}
