package com.arobinda.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arobinda.model.Photo;

public interface PhotoRepo extends JpaRepository<Photo, Integer>
{

	Optional<Photo> findByName(String name);

}
