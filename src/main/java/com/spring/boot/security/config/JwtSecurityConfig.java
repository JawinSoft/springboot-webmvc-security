package com.spring.boot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.boot.security.filter.ValidateJwtTokenFilter;
import com.spring.boot.security.service.AppSecurityUserDetailsService;

@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AppSecurityUserDetailsService userDetailsService;
	
	@Autowired
	private ValidateJwtTokenFilter validateJwtTokenFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	   auth.userDetailsService(userDetailsService);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http
		  .authorizeRequests()
		  .antMatchers("/create-token").permitAll()
		  .anyRequest().authenticated()
		  .and()
		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		http.addFilterBefore(validateJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
