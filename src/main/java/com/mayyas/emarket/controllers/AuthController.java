package com.mayyas.emarket.controllers;

import com.mayyas.emarket.dao.UserRepos;
import com.mayyas.emarket.dto.SignIn;
import com.mayyas.emarket.security.JWTService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emarket")
public class AuthController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepos userRepos;

    private final JWTService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JWTService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signin")
    public Map<String, String> token(@RequestBody SignIn userLogin) throws AuthenticationException {
 
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(),userLogin.getPassword() ));
        Integer id=userRepos.findByEmail(userLogin.getEmail()).get().getId();
        Map<String, String> res= new HashMap<>();
        res.put("token", tokenService.generateToken(userLogin.getEmail()));
        res.put("id",id.toString());
        return res;
    }
    @GetMapping("/getid/{token}")
    public Map<String, String> getId(@PathVariable("token") String token) {
 
    	String email=tokenService.extractUsernameFromToken(token);
        Integer id=userRepos.findByEmail(email).get().getId();
        Map<String, String> res= new HashMap<>();
        res.put("id",id.toString());
        return res;
    }
}
