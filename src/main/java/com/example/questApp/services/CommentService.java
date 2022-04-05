package com.example.questApp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.questApp.entities.Comment;
import com.example.questApp.entities.Post;
import com.example.questApp.entities.User;
import com.example.questApp.repos.CommentRepository;
import com.example.questApp.requests.CommentCreateRequest;
import com.example.questApp.requests.CommentUpdateRequest;
import com.example.questApp.responses.CommentResponse;

@Service
public class CommentService {	
	
	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;

	public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}
	public List<CommentResponse> getAllComments(Optional<Long> userId, Optional<Long> postId) {
		List<Comment> comments;
		if(userId.isPresent() && postId.isPresent())
			comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
		else if(userId.isPresent())
			comments = commentRepository.findByUserId(userId.get());
		else if(postId.isPresent())
			comments = commentRepository.findByPostId(postId.get());
		else
			comments = commentRepository.findAll();
		return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
	}
	
	public Comment createComment(CommentCreateRequest newComment) {		
		User user = userService.getUserById(newComment.getUserId());
		Post post = postService.getPostById(newComment.getPostId());
		if(user != null && post != null) {
			Comment commentToCreate = new Comment();
			commentToCreate.setId(newComment.getId());
			commentToCreate.setPost(post);
			commentToCreate.setText(newComment.getText());
			commentToCreate.setUser(user);
			return commentRepository.save(commentToCreate);
		}else 
			return null;
	}
	
	public Comment getCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}
	
	public Comment updateComment(Long commentId, CommentUpdateRequest request) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.isPresent()) {
			Comment commenToUpdate = new Comment();
			commenToUpdate.setText(request.getText());
			return commentRepository.save(commenToUpdate);
		}else
			return null;
	}
	public void deleteComment(Long commentId) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.isPresent()) {
			commentRepository.deleteById(commentId);

//			Comment commentToDelete = comment.get();
//			commentRepository.delete(commentToDelete);
		}
							
	}

}
