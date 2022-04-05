package com.example.questApp.responses;

import com.example.questApp.entities.User;

import lombok.Data;

@Data
public class UserResponse {

	private Long id;
	private int avatarId;
	private String username;
	
	public UserResponse(User entity) {
		this.id = entity.getId();
		this.avatarId = entity.getAvatar();
		this.username = entity.getUserName();				
	}
}
