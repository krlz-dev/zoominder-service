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
public class CreatePetRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Pet name is required")
    @Size(max = 100)
    private String name;

    @Positive(message = "Weight must be positive")
    private BigDecimal weightKg;

    private LocalDate birthdate;
}
