package com.example.questApp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.questApp.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUserId(Long userId);
	
	@Query(value = "select id from post where user_id = :userId  order by create_date desc", nativeQuery = true)
	List<Long> findTopByUserId(@Param("userId") Long userId);
}
