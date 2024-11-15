package com.arobinda.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arobinda.model.Complain;
import com.arobinda.service.UserService;


@RestController()
@RequestMapping("/executive")
public class ExecutiveController {
	
	@Autowired
	private UserService userService;

    @GetMapping("/getAllcomplain")
    public ResponseEntity<?> getAllcomplain() {
           
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", userService.getAllcomplain());
        return new ResponseEntity<>(response, HttpStatus.OK);
         
    }
    
    @PostMapping("/complainResolved")
    public ResponseEntity<?> markComplainResolved(@RequestBody Complain complain) throws IOException {
      
        return userService.markComplainResolved(complain);
    }
    
    @PostMapping("/complainRemarksUpdate")
    public ResponseEntity<?> complainRemarksUpdate(@RequestBody Complain complain ) {
           
    	 Map<String, Object> response = new HashMap<>();
    	Complain comObj=userService.complainRemarksUpdate(complain);
        if(comObj==null) {
        	
        	 response.put("status", "failed");
             response.put("data", null);
             return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
       
        response.put("status", "success");
        response.put("data", comObj);
        return new ResponseEntity<>(response, HttpStatus.OK);
         
    }
	
}
