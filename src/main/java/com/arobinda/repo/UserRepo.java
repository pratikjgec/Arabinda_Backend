package com.arobinda.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arobinda.model.Complain;
import com.arobinda.model.Myuser;
import com.arobinda.model.Notice;

@Repository
public interface UserRepo extends JpaRepository<Complain, Integer>{

	Optional<Complain> findByIdCard(String id);

	Optional<Complain> findByMobile(String mobile);

	List<Complain> findByStatus(String status);

	
}
