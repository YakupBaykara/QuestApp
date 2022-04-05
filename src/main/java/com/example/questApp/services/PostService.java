package com.example.questApp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questApp.entities.Post;
import com.example.questApp.entities.User;
import com.example.questApp.repos.PostRepository;
import com.example.questApp.requests.PostCreateRequest;
import com.example.questApp.requests.PostUpdateRequest;
import com.example.questApp.responses.LikeResponse;
import com.example.questApp.responses.PostResponse;

@Service
public class PostService {

	private PostRepository postRepository;
	private LikeService likeService;
	private UserService userService;

	public PostService(PostRepository postRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
	}
	
	// Setter Injection
	@Autowired
	public void setLikeService(LikeService likeService) {
		this.likeService = likeService;
	}

	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		
		List<Post> list;
		if (userId.isPresent()) 
			list =  postRepository.findByUserId(userId.get());		
		list = postRepository.findAll();
		
		return list.stream().map(p -> {
			List<LikeResponse> likes = likeService.getAllLikes(Optional.ofNullable(null), Optional.of(p.getId()));
			return new PostResponse(p, likes);
			}
		).collect(Collectors.toList());
	}

	public Post createPost(PostCreateRequest newPost) {
		User user = userService.getUserById(newPost.getUserId());
		if (user == null)
			return null;
		Post toSave = new Post();
		toSave.setId(newPost.getId());
		toSave.setText(newPost.getText());
		toSave.setTitle(newPost.getTitle());
		toSave.setUser(user);
		toSave.setCreateDate(new Date());
		return postRepository.save(toSave);
	}
	
	public Post getPostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}
 
	public PostResponse getPostByIdWithLikes(Long postId) {
		Post post = postRepository.findById(postId).orElse(null);
		List<LikeResponse> likes = likeService.getAllLikes(Optional.ofNullable(null), Optional.of(postId));
		return new PostResponse(post, likes);
	}

	public Post updatePost(Long postId, PostUpdateRequest request) {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			Post postToUpdate = post.get();
			postToUpdate.setText(request.getText());
			postToUpdate.setTitle(request.getTitle());
			postRepository.save(postToUpdate);
			return postToUpdate;
		}
		return null;
	}

	public void deletePost(Long postId) {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			postRepository.deleteById(postId);

//			Post postToDelete = post.get();
//			postRepository.delete(postToDelete);
		}

	}

}
