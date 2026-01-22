package com.zoominder.service.controller;

import com.zoominder.service.dto.CreatePetRequest;
import com.zoominder.service.dto.PetDTO;
import com.zoominder.service.dto.UpdatePetRequest;
import com.zoominder.service.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "Pet management endpoints")
public class PetController {

    private final PetService petService;

    @GetMapping
    @Operation(summary = "Get all pets")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pet by ID")
    public ResponseEntity<PetDTO> getPetById(@PathVariable UUID id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get pets by user ID")
    public ResponseEntity<List<PetDTO>> getPetsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(petService.getPetsByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "Create a new pet")
    public ResponseEntity<PetDTO> createPet(@Valid @RequestBody CreatePetRequest request) {
        PetDTO created = petService.createPet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing pet")
    public ResponseEntity<PetDTO> updatePet(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePetRequest request) {
        return ResponseEntity.ok(petService.updatePet(id, request));
    }

    @PostMapping("/{id}/photo")
    @Operation(summary = "Upload pet photo")
    public ResponseEntity<PetDTO> uploadPetPhoto(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(petService.updatePetPhoto(id, file.getBytes()));
    }

    @GetMapping("/{id}/photo")
    @Operation(summary = "Get pet photo")
    public ResponseEntity<byte[]> getPetPhoto(@PathVariable UUID id) {
        byte[] photo = petService.getPetPhoto(id);
        if (photo == null || photo.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a pet")
    public ResponseEntity<Void> deletePet(@PathVariable UUID id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
