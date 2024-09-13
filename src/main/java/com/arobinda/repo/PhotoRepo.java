package com.arobinda.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arobinda.model.Photo;
@Repository
public interface PhotoRepo extends JpaRepository<Photo, Integer>
{

	Optional<Photo> findByName(String name);

}
