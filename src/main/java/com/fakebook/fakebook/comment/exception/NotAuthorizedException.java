package com.fakebook.fakebook.comment.exception;

public class NotAuthorizedException extends RuntimeException {
	private static final String message = "자신이 작성한 댓글만 수정 및 삭제할 수 있습니다.";

	public NotAuthorizedException() {
		super(message);
	}
}
