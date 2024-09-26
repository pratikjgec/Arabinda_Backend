package com.arobinda.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    String systemDate=formatter.format(date); 
	 
	public String registerUser(Myuser user) {
		
		 if (adminRepo.findByUserName(user.getUserName()).isPresent()) {
	            return "User is already exist";
	       }
		 user.setPassword(passwordEncoder.encode(user.getPassword()));
		 user.setCreated_date(systemDate);
		 adminRepo.save(user);
		 
		 return "User registered successfully";
	}

	public List<Myuser> getUsers() {
		
		List<Myuser> userDeatils=new ArrayList<>();
		userDeatils=adminRepo.findAll();
		for(Myuser userObj:userDeatils) {
			userObj.setPassword(null);
		}
		return userDeatils;
	}

	public ResponseEntity<String> login(Myuser user) {
		
	    Optional<Myuser> userDetails=adminRepo.findByUserName(user.getUserName());
	    if(userDetails!=null && user.getPassword().equals(userDetails.get().getPassword())) {
	    	
	    	return ResponseEntity.ok("Login successfully");
        }
	    return ResponseEntity.badRequest().body("Incorrect UserName/Password !!");
		
	}
}
