package com.mayyas.emarket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mayyas.emarket.dao.AuthGroupRepos;
import com.mayyas.emarket.dao.UserRepos;


@Service
public class AppUserDetailService implements UserDetailsService {
    UserRepos userRepos;
    AuthGroupRepos authGroupRepos;

    @Autowired
    public AppUserDetailService(UserRepos userRepos, AuthGroupRepos authGroupRepos) {
        this.userRepos = userRepos;
        this.authGroupRepos = authGroupRepos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new AppUserPrincipal(
        		userRepos.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Email Not Found"))
                , authGroupRepos.findByEmail(username));
    }
}
