package com.zoominder.service.repository;

import com.zoominder.service.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {

    List<Pet> findByUserId(UUID userId);

    List<Pet> findByUserIdOrderByNameAsc(UUID userId);
}
