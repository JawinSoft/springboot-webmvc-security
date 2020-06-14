package com.spring.boot.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.security.model.CreateJwtRequest;
import com.spring.boot.security.service.JwtTokenService;

@RestController
public class HelloController {

	@Autowired
	private JwtTokenService jwtService;

	@Autowired
	private AuthenticationManager authManager;

	@Value("${basic-token}")
	private String basicToken;

	@GetMapping("/hi")
	public String sayHello() {
		return "Hello My Dear Friend Welcome to Spring Security with Json Web Token..!";
	}

	@PostMapping("/create-token")
	public String createJwtToke(@RequestBody CreateJwtRequest request) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());

		authManager.authenticate(token);//If User Name & Password is not Matching 
		
		return jwtService.createToken(request.getUsername());


	}

}
