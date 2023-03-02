package com.mayyas.emarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mayyas.emarket.models.Image;

public interface ImageRepos extends JpaRepository<Image, Integer> {
	
	
}
