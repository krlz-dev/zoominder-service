package com.zoominder.service.controller;

import com.zoominder.service.dto.CreateMedicationRequest;
import com.zoominder.service.dto.MedicationDTO;
import com.zoominder.service.dto.UpdateMedicationRequest;
import com.zoominder.service.service.MedicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
@Tag(name = "Medications", description = "Medication management endpoints")
public class MedicationController {

    private final MedicationService medicationService;

    @GetMapping
    @Operation(summary = "Get all medications")
    public ResponseEntity<List<MedicationDTO>> getAllMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get medication by ID")
    public ResponseEntity<MedicationDTO> getMedicationById(@PathVariable UUID id) {
        return ResponseEntity.ok(medicationService.getMedicationById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get medications by user ID")
    public ResponseEntity<List<MedicationDTO>> getMedicationsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(medicationService.getMedicationsByUserId(userId));
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Get medications by pet ID")
    public ResponseEntity<List<MedicationDTO>> getMedicationsByPetId(@PathVariable UUID petId) {
        return ResponseEntity.ok(medicationService.getMedicationsByPetId(petId));
    }

    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Get active medications by user ID")
    public ResponseEntity<List<MedicationDTO>> getActiveMedicationsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(medicationService.getActiveMedicationsByUserId(userId));
    }

    @GetMapping("/pet/{petId}/active")
    @Operation(summary = "Get active medications by pet ID")
    public ResponseEntity<List<MedicationDTO>> getActiveMedicationsByPetId(@PathVariable UUID petId) {
        return ResponseEntity.ok(medicationService.getActiveMedicationsByPetId(petId));
    }

    @PostMapping
    @Operation(summary = "Create a new medication")
    public ResponseEntity<MedicationDTO> createMedication(@Valid @RequestBody CreateMedicationRequest request) {
        MedicationDTO created = medicationService.createMedication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing medication")
    public ResponseEntity<MedicationDTO> updateMedication(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMedicationRequest request) {
        return ResponseEntity.ok(medicationService.updateMedication(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a medication")
    public ResponseEntity<Void> deleteMedication(@PathVariable UUID id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }
}
