package com.spring.boot.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.boot.security.service.JwtTokenService;

@Component
public class ValidateJwtTokenFilter  extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String bearerJwtToken = request.getHeader("Authorization");
		
		if(!StringUtils.isEmpty(bearerJwtToken)&& bearerJwtToken.startsWith("Bearer")) {
			
			String jwtToken = bearerJwtToken.substring(7);
			
			String userName = jwtService.extractUsername(jwtToken);
			
			if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
				
				
				UserDetails userDetails =userDetailsService.loadUserByUsername(userName);
				
				if(jwtService.validateToken(jwtToken, userName)) {
					
				   UsernamePasswordAuthenticationToken token = 
						   new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				   
				   token.setDetails(new WebAuthenticationDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(token);
					
					
					filterChain.doFilter(request, response);
				}else {
					throw new RuntimeException("Invalid Token to proceed..!");
				}
				
			}
			
			
		}
		
		filterChain.doFilter(request, response);
		
		
		
		
	}

}
