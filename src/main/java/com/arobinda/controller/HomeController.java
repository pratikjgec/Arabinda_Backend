package com.arobinda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arobinda.model.Complain;
import com.arobinda.model.User;
import com.arobinda.repo.HomeRepo;
import com.arobinda.service.HomeService;

@RestController
public class HomeController {
	

	
	@Autowired
	private HomeService homeService;
	
    @PostMapping("/complainRegister")
    public ResponseEntity<String> complainRegister(@RequestBody Complain complain) {
      
        return  homeService.complainRegister(complain);
    }
    
    @PostMapping("/getcomplain")
    public List<Complain> getcomplain(@RequestBody Complain complain) {
           
            return homeService.getComplain(complain);
    }
    @PostMapping("/getcomplainByMobile")
    public Optional<Complain> getcomplainByMobile(@RequestBody Complain complain) {
           
            return homeService.getComplainByMobile(complain);
    }
    
    @PutMapping("/complainResolved")
    public ResponseEntity<String> complainResolved(@RequestBody Complain complain) {
      
        return  homeService.complainResolved(complain);
    }
    
    
    

}
