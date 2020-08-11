package com.fakebook.fakebook.post.exception;

public class DoesNotExistingPostException extends RuntimeException {
    private static final String message = "존재하지 않는 게시글입니다. id = ";

    public DoesNotExistingPostException(Long id) {
        super(message);
    }
}
