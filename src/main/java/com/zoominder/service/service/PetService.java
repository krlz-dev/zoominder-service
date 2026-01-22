package com.zoominder.service.service;

import com.zoominder.service.dto.CreatePetRequest;
import com.zoominder.service.dto.PetDTO;
import com.zoominder.service.dto.UpdatePetRequest;
import com.zoominder.service.model.Pet;
import com.zoominder.service.model.User;
import com.zoominder.service.repository.PetRepository;
import com.zoominder.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PetDTO getPetById(UUID id) {
        return petRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PetDTO> getPetsByUserId(UUID userId) {
        return petRepository.findByUserIdOrderByNameAsc(userId).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public PetDTO createPet(CreatePetRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Pet pet = Pet.builder()
                .user(user)
                .name(request.getName())
                .weightKg(request.getWeightKg())
                .birthdate(request.getBirthdate())
                .build();

        Pet saved = petRepository.save(pet);
        return toDTO(saved);
    }

    @Transactional
    public PetDTO updatePet(UUID id, UpdatePetRequest request) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));

        if (request.getName() != null) {
            pet.setName(request.getName());
        }

        if (request.getWeightKg() != null) {
            pet.setWeightKg(request.getWeightKg());
        }

        if (request.getBirthdate() != null) {
            pet.setBirthdate(request.getBirthdate());
        }

        Pet saved = petRepository.save(pet);
        return toDTO(saved);
    }

    @Transactional
    public PetDTO updatePetPhoto(UUID id, byte[] photoData) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));

        pet.setPhotoData(photoData);
        Pet saved = petRepository.save(pet);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public byte[] getPetPhoto(UUID id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        return pet.getPhotoData();
    }

    @Transactional
    public void deletePet(UUID id) {
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }

    private PetDTO toDTO(Pet pet) {
        return PetDTO.builder()
                .id(pet.getId())
                .userId(pet.getUser().getId())
                .name(pet.getName())
                .weightKg(pet.getWeightKg())
                .birthdate(pet.getBirthdate())
                .hasPhoto(pet.getPhotoData() != null && pet.getPhotoData().length > 0)
                .createdAt(pet.getCreatedAt())
                .updatedAt(pet.getUpdatedAt())
                .build();
    }
}
