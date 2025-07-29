package com.catalis.core.lending.origination.interfaces.dtos.catalog.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskGradeDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long riskGradeId;

    private String code;
    private String name;
    private String description;
    private Boolean isActive;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}