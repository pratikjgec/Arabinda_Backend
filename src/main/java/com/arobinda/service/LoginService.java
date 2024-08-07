package com.arobinda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.arobinda.model.User;
import com.arobinda.repo.LoginRepo;

@Service
public class LoginService {
	
	
	@Autowired
	private LoginRepo loginRepo;

	public ResponseEntity<String> registerUser(User user) {
		
		 if (loginRepo.findByMobile(user.getMobile()).isPresent()) {
	            return ResponseEntity.badRequest().body("User is already exist");
	        }
		 loginRepo.save(user);
		 return ResponseEntity.ok("User registered successfully");
		
	}

	public List<User> getUsers() {
		return loginRepo.findAll();
	}

	public ResponseEntity<String> login(User user) {
		
	    Optional<User> userDetails=loginRepo.findByMobile(user.getMobile());
	    if(userDetails!=null && user.getPassword().equals(userDetails.get().getPassword())) {
	    	
	    	return ResponseEntity.ok("Login successfully");
        }
	    return ResponseEntity.badRequest().body("Incorrect UserName/Password !!");
		
		
	}
	
	
	

}
