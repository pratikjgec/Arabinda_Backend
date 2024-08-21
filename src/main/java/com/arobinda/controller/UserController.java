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
import org.springframework.web.bind.annotation.RestController;

import com.arobinda.model.Complain;
import com.arobinda.model.Content;
import com.arobinda.model.Notice;
import com.arobinda.model.PeopleSurvey;
import com.arobinda.model.SurveyData;
import com.arobinda.service.UserService;

@RestController
public class UserController {
	

	
	@Autowired
	private UserService userService;

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
   
    @PostMapping("/complainRegister")
    public ResponseEntity<String> complainRegister(@RequestBody Complain complain) {
      
        return  userService.complainRegister(complain);
    }
    
    @PostMapping("/getcomplain")
    public List<Complain> getcomplain(@RequestBody Complain complain) {
           
            return userService.getComplain(complain);
    }
    @GetMapping("/getcomplainByMobile")
    public Optional<Complain> getcomplainByMobile(@RequestBody Complain complain) {
           
            return userService.getComplainByMobile(complain);
    }
    
    @PutMapping("/complainResolved")
    public ResponseEntity<String> markComplainResolved(@RequestBody Complain complain) {
      
        return  userService.markComplainResolved(complain);
    }
    
    @PostMapping("/noticeSubmit")
    public ResponseEntity<String> noticeSubmit(@RequestBody Notice notice) {
           
            return userService.noticeSubmit(notice);
    }
    
    @GetMapping("/getNotice")
    public List<Notice> getNotice() {
           
            return userService.getNotice();
    }
    
    @PutMapping("/noticeInactive")
    public ResponseEntity<String> markNoticeInactive(@RequestBody Notice notice) {
      
        return  userService.markNoticeInactive(notice);
    }
    
    // content
    
    @PostMapping("/uploadAboutUs")
    public ResponseEntity<String> uploadAboutUs(@RequestBody Content content) {
           
            return userService.uploadAboutUs(content);
    }
    
    @GetMapping("/getAboutUs")
    public List<Content> getAboutUs() {
           
            return userService.getAboutUs();
    }
    
    @PutMapping("/updateAboutUs")
    public Optional<Content> updateAboutUs(@RequestBody Content content) {
      
        return  userService.updateAboutUs(content);
    }
    
    @PostMapping("/generateOTP")
    public String generateOTP(@RequestBody Complain complain) throws IOException {
           
            return userService.generateOTP(complain);
    }
    
    
    

}
