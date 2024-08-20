package com.arobinda.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arobinda.model.Otp;

@Repository
public interface OtpRepo extends JpaRepository<Otp, String>{

	Optional<Otp> findByMobile(String mobile);

	




	
	

}
