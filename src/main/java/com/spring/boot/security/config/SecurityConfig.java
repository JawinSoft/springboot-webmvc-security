package com.spring.boot.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class SecurityConfig extends  WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		  .jdbcAuthentication()
		  .dataSource(dataSource)
		  .usersByUsernameQuery("select username, password, enabled from  users where username = ?")
		  .authoritiesByUsernameQuery("select username, authority from authorities where username=?");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	     .authorizeRequests()
	     .antMatchers("/index").permitAll()
	     .antMatchers("/h2-console/**").permitAll()
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
		
		//return  NoOpPasswordEncoder.getInstance();
		
		return new BCryptPasswordEncoder();
	}
	
	
}
