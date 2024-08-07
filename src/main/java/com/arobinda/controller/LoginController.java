package com.arobinda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arobinda.model.User;
import com.arobinda.repo.LoginRepo;
import com.arobinda.service.LoginService;

@RestController
public class LoginController {

	
	@Autowired
    private LoginService loginService;
	@Autowired
	private LoginRepo loginRepo;

	@GetMapping("hello")
	public String health()
	{
		return "app running...";
	}
	
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
       
        return loginService.registerUser(user);
        
    }
    
    @GetMapping("/user")
    public List<User> getUsers() {
           
            return loginService.getUsers();
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
    
        
        return loginService.login(user);
    }
        
}
	
