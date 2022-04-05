package com.example.questApp.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questApp.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String username);

}
