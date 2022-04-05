package com.example.questApp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.entities.Like;
import com.example.questApp.requests.LikeCreateRequest;
import com.example.questApp.responses.LikeResponse;
import com.example.questApp.services.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
	
	private LikeService likeService;
	
	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}
	
	@GetMapping
	public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
		return likeService.getAllLikes(userId, postId);
	}
	
	@PostMapping
	public Like createLike(@RequestBody LikeCreateRequest newLike) {
		return likeService.createLike(newLike);
	}
	
	@GetMapping("/{likeId}")
	public Like getLikeById(@PathVariable Long likeId) {
		return likeService.getLikeById(likeId);
	}
	
	@DeleteMapping("/{likeId}")
	public void deleteLike(@PathVariable Long likeId) {
		likeService.deleteLike(likeId);
	}
}
