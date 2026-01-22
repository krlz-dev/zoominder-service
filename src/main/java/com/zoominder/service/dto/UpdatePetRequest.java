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
public class UpdatePetRequest {

    @Size(max = 100)
    private String name;

    @Positive(message = "Weight must be positive")
    private BigDecimal weightKg;

    private LocalDate birthdate;
}
