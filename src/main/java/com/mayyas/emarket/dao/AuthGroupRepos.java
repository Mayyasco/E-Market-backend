package com.mayyas.emarket.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayyas.emarket.models.AuthGroup;

public interface AuthGroupRepos extends JpaRepository<AuthGroup,Integer> {
	
	List<AuthGroup> findByEmail(String email);
	
}
