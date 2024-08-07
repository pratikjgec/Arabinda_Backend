package com.arobinda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.arobinda.model.Complain;
import com.arobinda.model.User;
import com.arobinda.repo.HomeRepo;

@Service
public class HomeService {
	
	
	@Autowired
	private HomeRepo homeRepo;

	public ResponseEntity<String> complainRegister(Complain complain) {
		
		 if (homeRepo.findByIdCard(complain.getIdCard()).isPresent()) {
	         return ResponseEntity.badRequest().body("Complain is already registered!!");
	     }
		 homeRepo.save(complain);
		 return ResponseEntity.ok("Complain registered successfully");
	}

	public List<Complain> getComplain(Complain complain) {
		
		return homeRepo.findByStatus(complain.getStatus());
		
	}
	
	public Optional<Complain> getComplainByMobile(Complain complain) {
		
		Optional<Complain> complainDetails=homeRepo.findByMobile(complain.getMobile());
	    if(complainDetails!=null) {
	    	
	    	
	    	return complainDetails;
        }
	    return null;
		
	}

	public ResponseEntity<String> complainResolved(Complain complain) {
		
		Optional<Complain> complainDetails=homeRepo.findByMobile(complain.getMobile());
		 if (complainDetails!=null) {
			 
			 Complain complainData=complainDetails.get();
			 complainData.setStatus(complain.getStatus());
			 homeRepo.save(complainData);
			 return ResponseEntity.ok("Complain Resolved!!");
		 }
			 
			 
	         return ResponseEntity.badRequest().body("Complain not found!!");
	     
		
	}
	


}
