package com.spring.boot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class JPASecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	     .authorizeRequests()
	     .antMatchers("/index").permitAll()
	     .antMatchers("/user/**").authenticated()
	     .antMatchers("/admin/**").hasAnyRole("ADMIN")
	     .antMatchers("/employee/**").hasAnyRole("ADMIN","EMPLOYEE")
	     .and()
	     .logout().deleteCookies("JSESSIONID").logoutSuccessUrl("/index")
	     .and()
	     .csrf().disable()
	     .formLogin();
	     
	    http.headers().frameOptions().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
