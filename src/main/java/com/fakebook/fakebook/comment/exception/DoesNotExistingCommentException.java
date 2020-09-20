package com.fakebook.fakebook.comment.exception;

public class DoesNotExistingCommentException extends RuntimeException {
	private static final String message = "존재하지 않는 댓글입니다. id =";

	public DoesNotExistingCommentException(Long id) {
		super(message + id);
	}
}
