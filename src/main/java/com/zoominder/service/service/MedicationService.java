package com.zoominder.service.service;

import com.zoominder.service.dto.CreateMedicationRequest;
import com.zoominder.service.dto.MedicationDTO;
import com.zoominder.service.dto.UpdateMedicationRequest;
import com.zoominder.service.model.Medication;
import com.zoominder.service.model.Pet;
import com.zoominder.service.model.User;
import com.zoominder.service.repository.MedicationRepository;
import com.zoominder.service.repository.PetRepository;
import com.zoominder.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public List<MedicationDTO> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public MedicationDTO getMedicationById(UUID id) {
        return medicationRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Medication not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<MedicationDTO> getMedicationsByUserId(UUID userId) {
        return medicationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MedicationDTO> getMedicationsByPetId(UUID petId) {
        return medicationRepository.findByPetId(petId).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MedicationDTO> getActiveMedicationsByUserId(UUID userId) {
        return medicationRepository.findByUserIdAndIsActiveTrue(userId).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MedicationDTO> getActiveMedicationsByPetId(UUID petId) {
        return medicationRepository.findByPetIdAndIsActiveTrue(petId).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public MedicationDTO createMedication(CreateMedicationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + request.getPetId()));

        Medication medication = Medication.builder()
                .user(user)
                .pet(pet)
                .name(request.getName())
                .amount(request.getAmount())
                .unit(request.getUnit())
                .frequencyType(request.getFrequencyType())
                .selectedWeekdays(request.getSelectedWeekdays())
                .dayOfMonth(request.getDayOfMonth())
                .specificDate(request.getSpecificDate())
                .times(request.getTimes())
                .notes(request.getNotes())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .durationType(request.getDurationType())
                .durationValue(request.getDurationValue())
                .build();

        Medication saved = medicationRepository.save(medication);
        return toDTO(saved);
    }

    @Transactional
    public MedicationDTO updateMedication(UUID id, UpdateMedicationRequest request) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found with id: " + id));

        if (request.getName() != null) {
            medication.setName(request.getName());
        }
        if (request.getAmount() != null) {
            medication.setAmount(request.getAmount());
        }
        if (request.getUnit() != null) {
            medication.setUnit(request.getUnit());
        }
        if (request.getFrequencyType() != null) {
            medication.setFrequencyType(request.getFrequencyType());
        }
        if (request.getSelectedWeekdays() != null) {
            medication.setSelectedWeekdays(request.getSelectedWeekdays());
        }
        if (request.getDayOfMonth() != null) {
            medication.setDayOfMonth(request.getDayOfMonth());
        }
        if (request.getSpecificDate() != null) {
            medication.setSpecificDate(request.getSpecificDate());
        }
        if (request.getTimes() != null) {
            medication.setTimes(request.getTimes());
        }
        if (request.getNotes() != null) {
            medication.setNotes(request.getNotes());
        }
        if (request.getIsActive() != null) {
            medication.setIsActive(request.getIsActive());
        }
        if (request.getStartDate() != null) {
            medication.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            medication.setEndDate(request.getEndDate());
        }
        if (request.getDurationType() != null) {
            medication.setDurationType(request.getDurationType());
        }
        if (request.getDurationValue() != null) {
            medication.setDurationValue(request.getDurationValue());
        }

        Medication saved = medicationRepository.save(medication);
        return toDTO(saved);
    }

    @Transactional
    public void deleteMedication(UUID id) {
        if (!medicationRepository.existsById(id)) {
            throw new RuntimeException("Medication not found with id: " + id);
        }
        medicationRepository.deleteById(id);
    }

    private MedicationDTO toDTO(Medication medication) {
        return MedicationDTO.builder()
                .id(medication.getId())
                .userId(medication.getUser().getId())
                .petId(medication.getPet().getId())
                .petName(medication.getPet().getName())
                .name(medication.getName())
                .amount(medication.getAmount())
                .unit(medication.getUnit())
                .frequencyType(medication.getFrequencyType())
                .selectedWeekdays(medication.getSelectedWeekdays())
                .dayOfMonth(medication.getDayOfMonth())
                .specificDate(medication.getSpecificDate())
                .times(medication.getTimes())
                .notes(medication.getNotes())
                .isActive(medication.getIsActive())
                .startDate(medication.getStartDate())
                .endDate(medication.getEndDate())
                .durationType(medication.getDurationType())
                .durationValue(medication.getDurationValue())
                .createdAt(medication.getCreatedAt())
                .updatedAt(medication.getUpdatedAt())
                .build();
    }
}
