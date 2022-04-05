package com.example.questApp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.questApp.entities.User;
import com.example.questApp.repos.CommentRepository;
import com.example.questApp.repos.LikeRepository;
import com.example.questApp.repos.PostRepository;
import com.example.questApp.repos.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	private LikeRepository likeRepository;
	private CommentRepository commentRepository;
	
	

	public UserService(UserRepository userRepository, PostRepository postRepository, LikeRepository likeRepository,
			CommentRepository commentRepository) {
		super();
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
	}

	public List<User> getAllUsers() {		
		return userRepository.findAll();
	}

	public User createUser(User newUser) {
		return userRepository.save(newUser);
	}

	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User updateUser(Long userId, User newUser) {		
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			User userToUpdate = user.get();
			userToUpdate.setUserName(newUser.getUserName());
			userToUpdate.setPassword(newUser.getPassword());
			userToUpdate.setAvatar(newUser.getAvatar());
			userRepository.save(userToUpdate);
			return userToUpdate;
		}else
			return null;
	}

	public void deleteUser(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			userRepository.deleteById(userId);
//			User userToDelete = user.get();
//			userRepository.delete(userToDelete);
		}
	}

	public User getUserByUsername(String username) {
		
		return userRepository.findByUserName(username);
	}

	public List<Object> getUserActivity(Long userId) {
		List<Long> postIds = postRepository.findTopByUserId(userId);
		if(postIds.isEmpty())
			return null;
		List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
		List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		return result;
	}
}
