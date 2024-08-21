package com.arobinda.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arobinda.model.Myuser;
import com.arobinda.repo.AdminRepo;

@Service
public class AdminService {
	
	
	@Autowired
	private AdminRepo adminRepo;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;

	public ResponseEntity<String> registerUser(Myuser user) {
		
		 if (adminRepo.findByUserName(user.getUserName()).isPresent()) {
	            return ResponseEntity.badRequest().body("User is already exist");
	       }

		 user.setPassword(passwordEncoder.encode(user.getPassword()));
		 adminRepo.save(user);
		 
		 return ResponseEntity.ok("User registered successfully");
		 
		
	}

	public List<Myuser> getUsers() {
		return adminRepo.findAll();
	}

	public ResponseEntity<String> login(Myuser user) {
		
	    Optional<Myuser> userDetails=adminRepo.findByUserName(user.getUserName());
	    if(userDetails!=null && user.getPassword().equals(userDetails.get().getPassword())) {
	    	
	    	return ResponseEntity.ok("Login successfully");
        }
	    return ResponseEntity.badRequest().body("Incorrect UserName/Password !!");
		
		
	}

	
		

	
	
	

}