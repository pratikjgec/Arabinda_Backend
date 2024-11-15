package com.arobinda.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arobinda.model.Notice;

@Repository
public interface NoticeRepo extends JpaRepository<Notice, Integer>{

	@Query(value = "SELECT * FROM notice where is_active=1", nativeQuery = true)
	List<Notice> findAllActiveNotice();



}
