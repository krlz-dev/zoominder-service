package com.zoominder.service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationDTO {
    private UUID id;
    private UUID userId;
    private UUID petId;
    private String petName;
    private String name;
    private BigDecimal amount;
    private String unit;
    private String frequencyType;
    private String[] selectedWeekdays;
    private Integer dayOfMonth;
    private LocalDate specificDate;
    private String times;
    private String notes;
    private Boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;
    private String durationType;
    private Integer durationValue;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
