package com.zoominder.service.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMedicationRequest {

    @Size(max = 200)
    private String name;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @Size(max = 50)
    private String unit;

    @Size(max = 20)
    private String frequencyType;

    private String[] selectedWeekdays;

    private Integer dayOfMonth;

    private LocalDate specificDate;

    private String times;

    private String notes;

    private Boolean isActive;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 20)
    private String durationType;

    private Integer durationValue;
}
