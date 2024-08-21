package com.arobinda.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arobinda.model.Facility;
@Repository
public interface FacilityRepo extends JpaRepository<Facility, Integer>{

	@Query(value = "SELECT * FROM facility where id=:facility_id", nativeQuery = true)
	Facility findByFacilityId(@Param("facility_id") int facility_id);

}
