package com.spring.boot.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.Optional;

import com.spring.boot.security.model.AppUser;
import com.spring.boot.security.repository.AppUserRepository;

@Service
public class AppSecurityUserDetailsService implements UserDetailsService{

	@Autowired
	private AppUserRepository repository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<AppUser> dbUser = repository.findByUserName(username);
		
		//if user not found on the db throw user not found exception
		dbUser.orElseThrow(() ->  new UsernameNotFoundException("User Not Found "+username));
		
		return User
				.builder()
				.username(dbUser.get().getUserName())
				.password(dbUser.get().getPassword())
				.disabled(!dbUser.get().getActive())
				.authorities(isEmpty(dbUser.get().getRoles()) ? null : dbUser.get().getRoles().split(","))
				.build();
		
	}

	
	
	
	
	
}
