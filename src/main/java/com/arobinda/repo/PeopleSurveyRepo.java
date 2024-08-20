package com.arobinda.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arobinda.model.PeopleSurvey;

public interface PeopleSurveyRepo extends JpaRepository<PeopleSurvey, Integer>{

}
