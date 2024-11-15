package com.arobinda.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

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

		String systemDate() {
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
		    formatter.setTimeZone(TimeZone.getTimeZone("IST"));
		    return formatter.format(date); 
			}
	 
	public String registerUser(Myuser user) {
		
		 if (adminRepo.findByUserName(user.getUserName()).isPresent()) {
	            return "User is already exist";
	       }
		 user.setPassword(passwordEncoder.encode(user.getPassword()));
		 user.setCreated_date(systemDate());
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

	public String activeInactiveUser(Myuser userDetails) {
		
		Optional<Myuser> user=adminRepo.findById(userDetails.getId());
		String msg="";
		if(user.isPresent()) {
			if(userDetails.getStatus().equalsIgnoreCase("active")) {
				user.get().setActive(1);
				msg= "User Activated Successfully...!!!";
			}
			else {
				user.get().setActive(0);
				msg= "User Inactive Successfully...!!!";
			}
			user.get().setInActive_by(userDetails.getInActive_by());
			user.get().setInActive_date(systemDate());
			adminRepo.save(user.get());
			return msg;
		}
		return "No Valid User found..!!";
	}
}
