package com.zoominder.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMedicationRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Pet ID is required")
    private UUID petId;

    @NotBlank(message = "Medication name is required")
    @Size(max = 200)
    private String name;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Unit is required")
    @Size(max = 50)
    private String unit;

    @NotBlank(message = "Frequency type is required")
    @Size(max = 20)
    private String frequencyType;

    private String[] selectedWeekdays;

    private Integer dayOfMonth;

    private LocalDate specificDate;

    @NotBlank(message = "Times is required")
    private String times;

    private String notes;

    private Boolean isActive;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 20)
    private String durationType;

    private Integer durationValue;
}
