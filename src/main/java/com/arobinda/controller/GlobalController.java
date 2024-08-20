package com.arobinda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {
	
	@GetMapping("/home")
	public String homePage()
	{
		return "index";
	}
	
	@GetMapping("/admin/registration")
	public String userCreate()
	{
		return "registration";
	}
	
	@GetMapping("/user/complain")
	public String adminlogin()
	{
		return "complain.html";
	}
	
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	
	

	

}
