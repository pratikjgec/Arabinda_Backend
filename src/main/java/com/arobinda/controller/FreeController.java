package com.arobinda.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arobinda.model.Complain;
import com.arobinda.model.Content;
import com.arobinda.model.Notice;
import com.arobinda.service.UserService;

@RestController
public class FreeController {
	
	
	@Autowired
	private UserService userService;
	
    @PostMapping("/generateOTP")
    public String generateOTP(@RequestBody Complain complain) throws IOException {
           
            return userService.generateOTP(complain);
    }

    @GetMapping("/getAboutUs")
    public List<Content> getAboutUs() {
           
            return userService.getAboutUs();
    }
    
    @GetMapping("/getNotice")
    public List<Notice> getNotice() {
           
            return userService.getNotice();
    }
	
    @GetMapping("/getcomplainByMobile")
    public Optional<Complain> getcomplainByMobile(@RequestBody Complain complain) {
           
            return userService.getComplainByMobile(complain);
    }
	
    @PostMapping("/complainRegister")
    public ResponseEntity<String> complainRegister(@RequestBody Complain complain) {
      
        return  userService.complainRegister(complain);
    }
}
