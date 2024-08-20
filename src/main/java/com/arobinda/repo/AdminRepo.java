package com.arobinda.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arobinda.model.Myuser;

@Repository
public interface AdminRepo extends JpaRepository<Myuser, Integer>{

	Optional<Myuser> findByUserName(String mobile);

	
	
	

}
