package com.mayyas.emarket.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mayyas.emarket.dao.UserRepos;
import com.mayyas.emarket.models.User;
import com.mayyas.emarket.service.UpdatePasswordService;

@SpringBootTest
public class UpdatePasswordServiceTest {
	@Autowired
	UpdatePasswordService updatePasswordService;
	@Autowired
	private UserRepos userRepos;
	
	@Test
	void testUpdatePassworService() {
		//change password to user 1 to 7777
		updatePasswordService.updateUserPass(1, "9999", "7777");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4); 
		User u = userRepos.findById(1).get();
		assertTrue(encoder.matches("7777",u.getPassword()));
		
}
}