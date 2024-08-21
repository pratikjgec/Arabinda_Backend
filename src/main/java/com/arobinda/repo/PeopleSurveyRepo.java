package com.arobinda.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arobinda.model.PeopleSurvey;

@Repository
public interface PeopleSurveyRepo extends JpaRepository<PeopleSurvey, Integer>{

	@Query(value = "SELECT * FROM people_survey WHERE familyHead = 1", nativeQuery = true)
	List<PeopleSurvey> findAllFamilyHead();
	
	@Query(value = "SELECT * FROM people_survey where facility_id=:facility_id", nativeQuery = true)
	List<PeopleSurvey> findAllByFacilityId(@Param("facility_id") int facility_id);
	
	

}
