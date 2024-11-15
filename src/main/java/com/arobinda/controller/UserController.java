package com.arobinda.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.arobinda.model.Complain;
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
    public ResponseEntity<?> surveyRegister(@RequestBody SurveyData surveyData) throws Exception {
      
    	 Map<String, Object> response = new HashMap<>();
         response.put("status", "success");
         response.put("message", userService.surveyRegister(surveyData));
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllFamilyHead")
    public ResponseEntity<?> getAllFamilyHead()  {
      
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", userService.getAllFamilyHead());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getSurveyData/{id}")
    public ResponseEntity<?> getSurveyData(@PathVariable("id") int facility_id)  {
      
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", userService.getSurveyData(facility_id));
        return new ResponseEntity<>(response, HttpStatus.OK);
       
    }
   

    
   
}
