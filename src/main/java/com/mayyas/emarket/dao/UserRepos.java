package com.mayyas.emarket.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mayyas.emarket.models.User;


public interface UserRepos extends JpaRepository<User, Integer> {
	
			   
	Optional<User> findByEmail(String email);
}
