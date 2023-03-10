package com.mayyas.emarket.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mayyas.emarket.dao.UserRepos;

import com.mayyas.emarket.models.User;


@Service
public class UpdatePasswordService {
	@Autowired
	private UserRepos userRepos;
	public Map<String, String> updateUserPass(@PathVariable("id") int id, String old,String nw) {
		User u = userRepos.findById(id).get();
		//check old password same as new password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);  
		if (encoder.matches(old,u.getPassword())) {
			u.setPassword(new BCryptPasswordEncoder(4).encode(nw));
			userRepos.save(u);
			return Collections.singletonMap("ms", "ok");
		} else
			return Collections.singletonMap("ms", "the old password not correct");

	}


}
