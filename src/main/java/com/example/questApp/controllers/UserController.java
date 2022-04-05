package com.example.questApp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.entities.User;
import com.example.questApp.responses.UserResponse;
import com.example.questApp.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping
	public User createUser(@RequestBody User newUser) {
		return userService.createUser(newUser);
	}
	
	@GetMapping("/{userId}")      //	Path, /users/Id oluyor. /users pathine eklemiş oluyor.	
	public UserResponse getUserById(@PathVariable Long userId) {
		return new UserResponse(userService.getUserById(userId));
	}
	
	@PutMapping("/{userId}")
	public User updateUser(@PathVariable Long userId, @RequestBody User newUser) {
		return userService.updateUser(userId, newUser);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
	}
	
	@GetMapping("/activity/{userId}")
	public List<Object> getUserActivity(@PathVariable Long userId) {
		return userService.getUserActivity(userId);
	}
}
