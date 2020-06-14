package com.spring.boot.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.spring.boot.security.model.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Integer>{

	Optional<AppUser> findByUserName(String userName);
	
}
