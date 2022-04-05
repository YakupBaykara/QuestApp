package com.example.questApp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.entities.Comment;
import com.example.questApp.requests.CommentCreateRequest;
import com.example.questApp.requests.CommentUpdateRequest;
import com.example.questApp.responses.CommentResponse;
import com.example.questApp.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	private CommentService commentService;

	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping
	public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
		return commentService.getAllComments(userId, postId);
	}
	
	@PostMapping
	public Comment createComment(@RequestBody CommentCreateRequest newComment) {
		return commentService.createComment(newComment);
	}
	
	@GetMapping("/{commentId}")
	public Comment getCommentById(@PathVariable Long commentId) {
		return commentService.getCommentById(commentId);
	}
	
	@PutMapping("/{commentId}")
	public Comment updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
		return commentService.updateComment(commentId, request);
	}
	
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable Long commentId) {
		commentService.deleteComment(commentId);
	}
}
