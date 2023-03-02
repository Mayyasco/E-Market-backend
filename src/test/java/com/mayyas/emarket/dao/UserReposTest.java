package com.mayyas.emarket.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.mayyas.emarket.models.House;
import com.mayyas.emarket.models.User;

@SpringBootTest
class UserReposTest {
	@Autowired
	private UserRepos userRepos;
	
	@Test
	void testSignin() {
		//user mayyasco@yahoo.com pw:2222 id:2
		Integer actual = userRepos.signin("mayyasco@yahoo.com", "2222");
		assertThat(actual).isEqualTo(2);
	}

	/*@Test
	void testSave() {
		User u = new User();
		u.setName("test");
		u.setPassword("test1");
		u.setEmail("test@yahoo.com");
		u.setAddress("Amman");
		u.setPhone("313-89745");
		User u2 = userRepos.save(u);
		assertThat(u2).isNotNull();
	}*/
	@Test
	//try to save without password
	void testSaveUserException() {
		assertThrows(Exception.class,
	            ()->{
	            	User u = new User();
	        		u.setName("test");
	        		u.setEmail("test@yahoo.com");
	        		u.setAddress("Amman");
	        		u.setPhone("313-89745");
	        		userRepos.save(u);
	            });
	}
	
	@Test
	void testFindById() {
		User expected = new User();expected.setId(2);
		User actual = userRepos.findById(2).get();
		assertThat(actual).isEqualTo(expected);
	}
}
