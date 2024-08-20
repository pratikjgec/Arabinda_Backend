package com.arobinda.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arobinda.model.Facility;

public interface FacilityRepo extends JpaRepository<Facility, Integer>{

}
