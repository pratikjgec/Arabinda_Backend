package com.arobinda.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arobinda.model.Notice;

@Repository
public interface NoticeRepo extends JpaRepository<Notice, Integer>{



}
