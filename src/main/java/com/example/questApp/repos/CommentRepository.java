package com.example.questApp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.questApp.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByUserIdAndPostId(Long userId, Long postId);

	List<Comment> findByUserId(Long userId);

	List<Comment> findByPostId(Long postId);
	
	@Query(value = "select 'commented on', c.post_id, u.avatar, u.user_name from"
				   + "comment c left koin user u on u.id = c.user_id"
				   + "where c.post_id in :postIds",nativeQuery = true)
	List<Object> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);

}
