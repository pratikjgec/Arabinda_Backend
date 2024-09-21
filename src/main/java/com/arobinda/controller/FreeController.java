package com.arobinda.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arobinda.config.MyUserDetailsService;
import com.arobinda.model.Complain;
import com.arobinda.model.Myuser;
import com.arobinda.repo.AdminRepo;
import com.arobinda.service.UserService;
import com.arobinda.webtoken.JwtService;
import com.arobinda.webtoken.LoginForm;

@RestController
public class FreeController {
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private AdminRepo adminRepo;

    
    @PostMapping("/generateOTP")
    public ResponseEntity<?> generateOTP(@RequestBody Complain complain) throws IOException {
           
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", userService.generateOTP(complain));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAboutUs")
    public ResponseEntity<?> getAboutUs() {
           
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", userService.getAboutUs());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getNotice")
    public ResponseEntity<?> getNotice() {
           
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", userService.getNotice());
        return new ResponseEntity<>(response, HttpStatus.OK);
           
    }
	
    @PostMapping("/getComplainByComplainId")
    public ResponseEntity<?> getComplainByComplainId(@RequestBody Complain complain) {
           
    	Optional<Complain> userComplain=userService.getComplainByComplainId(complain.getComplain_id());
    	Map<String, Object> response = new HashMap<>();
    	if(userComplain!=null) {
	        response.put("status", "success");
	        response.put("data", userComplain);
	        return new ResponseEntity<>(response, HttpStatus.OK);
    	}
    	else {
    		 response.put("status", "failed");
 	        response.put("message", "Complain id-"+complain.getComplain_id() +" no active complain found..");
 	        return new ResponseEntity<>(response, HttpStatus.OK);
    	}
             
    }
	
    @PostMapping("/complainRegister")
    public ResponseEntity<?> complainRegister(@RequestBody Complain complain) throws IOException {
      
          return userService.complainRegister(complain);
    }

    
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginForm loginForm) {
    	try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
	        if (authentication.isAuthenticated()) {
	            String token= jwtService.generateToken(myUserDetailsService.loadUserByUsername(loginForm.username()));
	            Optional<Myuser> user=adminRepo.findByUserName(loginForm.username());
	            
	                Map<String, Object> response = new HashMap<>();
	                response.put("status", "success");
	                response.put("message", "Token Created successfully");
	                response.put("full_name", user.get().getFull_name());
	                response.put("role",user.get().getRole() );
	                response.put("token", token);
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            
	        } else {
	        
	            Map<String, Object> response = new HashMap<>();
                response.put("status", "failed");
                response.put("message", "Invalid Username/Password!!!");
                return new ResponseEntity<>(response, HttpStatus.OK);
	        }
    	}
    	catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "failed");
            response.put("message", "Invalid Username/Password!!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    
}
