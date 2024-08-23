package com.arobinda.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arobinda.model.Complain;
import com.arobinda.model.Content;
import com.arobinda.model.Notice;
import com.arobinda.model.PeopleSurvey;
import com.arobinda.model.SurveyData;
import com.arobinda.service.UserService;

@RestController()
@RequestMapping("/user")
public class UserController {
	

	
	@Autowired
	private UserService userService;
	
    @GetMapping("user/test")
    public String test() {
        return "after login user request test";
    }

    @PostMapping("/surveyRegister")
    public String surveyRegister(@RequestBody SurveyData surveyData) throws Exception {
      
        return userService.surveyRegister(surveyData);
    }
    
    @GetMapping("/getAllFamilyHead")
    public List<PeopleSurvey> getAllFamilyHead()  {
      
        return userService.getAllFamilyHead();
    }
    @GetMapping("/getSurveyData/{id}")
    public SurveyData getSurveyData(@PathVariable("id") int facility_id)  {
      
        return userService.getSurveyData(facility_id);
    }
   

    
    @PostMapping("/getcomplain")
    public List<Complain> getcomplain(@RequestBody Complain complain) {
           
            return userService.getComplain(complain);
    }

    
    @PutMapping("/complainResolved")
    public ResponseEntity<String> markComplainResolved(@RequestBody Complain complain) {
      
        return  userService.markComplainResolved(complain);
    }

    


    
    // content
    


    

    

    
    
    

}
