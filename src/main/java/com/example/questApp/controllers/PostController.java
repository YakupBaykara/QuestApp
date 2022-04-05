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

import com.example.questApp.entities.Post;
import com.example.questApp.requests.PostCreateRequest;
import com.example.questApp.requests.PostUpdateRequest;
import com.example.questApp.responses.PostResponse;
import com.example.questApp.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
		return postService.getAllPosts(userId);
	}
	
	@PostMapping
	public Post createPost(@RequestBody PostCreateRequest newPost) {
		return postService.createPost(newPost);
	}
 	
	@GetMapping("/{postId}")
	public PostResponse getPostById(@PathVariable Long postId) {
		return postService.getPostByIdWithLikes(postId);	
	}
	
	@PutMapping("/{postId}")
	public Post updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
		return postService.updatePost(postId, request);
	}
	
	@DeleteMapping("/{postId}")
	public void deletePost(@PathVariable Long postId) {
		postService.deletePost(postId);
	}
}
