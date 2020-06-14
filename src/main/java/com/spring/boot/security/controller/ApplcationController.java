package com.spring.boot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplcationController {

	
	
	@GetMapping("index")
	public String home() {
		return "index";
	}
	
	@GetMapping("/user/index")
	public String userHome() {
		return "/user/index";
	}
	
	

	@GetMapping("/admin/index")
	public String adminHome() {
		return "/admin/index";
	}
	
	
	@GetMapping("/employee/index")
	public String employeeHome() {
		return "/employee/index";
	}
	
	
	
	
}
