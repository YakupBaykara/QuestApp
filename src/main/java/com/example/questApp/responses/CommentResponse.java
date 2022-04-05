package com.example.questApp.responses;

import com.example.questApp.entities.Comment;
import lombok.Data;

@Data
public class CommentResponse {

	private Long id;
	private Long userId;
	private String username;
	private String text;
	
	public CommentResponse(Comment entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.username = entity.getUser().getUserName();
		this.text = entity.getText();
	}
}
