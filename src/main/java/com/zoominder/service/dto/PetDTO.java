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
public class PetDTO {
    private UUID id;
    private UUID userId;
    private String name;
    private BigDecimal weightKg;
    private LocalDate birthdate;
    private boolean hasPhoto;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
