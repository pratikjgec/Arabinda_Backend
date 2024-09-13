package com.arobinda.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arobinda.model.Content;

@Repository
public interface ContentRepo extends JpaRepository<Content, Integer>{

}
