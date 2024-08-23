package com.arobinda.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.arobinda.model.Complain;
import com.arobinda.model.Content;
import com.arobinda.model.Notice;
import com.arobinda.service.UserService;

@Controller
public class GlobalController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/home")
	public String homePage()
	{
		return "index";
	}

    
	@GetMapping("/complain")
	public String adminlogin()
	{
		return "complain.html";
	}
	
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	


    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
        
    }
	

}
