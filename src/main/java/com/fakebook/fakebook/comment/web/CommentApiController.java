package com.fakebook.fakebook.comment.web;

import com.fakebook.fakebook.comment.service.CommentService;
import com.fakebook.fakebook.comment.web.dto.CommentRegisterRequestDto;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/comment")
@RestController
public class CommentApiController {
	private final CommentService commentService;

	public CommentApiController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/register")
	public Long register(@RequestBody CommentRegisterRequestDto requestDto,
						 Long postId, Principal principal) {
		String userId = principal.getName();
		return commentService.register(requestDto, userId, postId);
	}

	@PostMapping("/delete/{commentId}")
	public Long update(@PathVariable Long commentId) {
		return commentService.delete(commentId);
	}
}
