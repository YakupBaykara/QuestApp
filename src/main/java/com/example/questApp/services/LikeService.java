package com.example.questApp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.questApp.entities.Like;
import com.example.questApp.entities.Post;
import com.example.questApp.entities.User;
import com.example.questApp.repos.LikeRepository;
import com.example.questApp.repos.PostRepository;
import com.example.questApp.repos.UserRepository;
import com.example.questApp.requests.LikeCreateRequest;
import com.example.questApp.responses.LikeResponse;

@Service
public class LikeService {
	
	private LikeRepository likeRepository;
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
		this.likeRepository = likeRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository; 
	}

	public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
		
		List<Like> list;
		if(userId.isPresent() && postId.isPresent())
			list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		else if(userId.isPresent())
			list = likeRepository.findByUserId(userId.get());
		else if(postId.isPresent())
			list = likeRepository.findByPostId(postId.get());
		else
			list = likeRepository.findAll();
		return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}

	public Like createLike(LikeCreateRequest newLike) {
		User user = userRepository.getById(newLike.getUserId());
		Post post = postRepository.getById(newLike.getPostId());
		if(user != null && post != null) {
			Like likeToCreate = new Like();
			likeToCreate.setId(newLike.getId());
			likeToCreate.setUser(user);
			likeToCreate.setPost(post);
			return likeRepository.save(likeToCreate);
		}else
			return null;		
	}

	public Like getLikeById(Long likeId) {
		return likeRepository.findById(likeId).orElse(null);
	}

	public void deleteLike(Long likeId) {
		Optional<Like> like = likeRepository.findById(likeId);
		if(like.isPresent())
			likeRepository.deleteById(likeId);
	}

}
