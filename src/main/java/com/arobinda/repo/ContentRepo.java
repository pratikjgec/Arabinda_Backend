package com.arobinda.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arobinda.model.Content;

public interface ContentRepo extends JpaRepository<Content, Integer>{

}
