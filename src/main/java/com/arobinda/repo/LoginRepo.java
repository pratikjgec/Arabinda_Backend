package com.arobinda.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arobinda.model.User;

@Repository
public interface LoginRepo extends JpaRepository<User, Integer>{

	Optional<User> findByMobile(String mobile);

	
	
	

}
