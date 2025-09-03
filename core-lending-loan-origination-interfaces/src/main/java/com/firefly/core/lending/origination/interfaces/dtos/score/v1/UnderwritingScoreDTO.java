package com.firefly.core.lending.origination.interfaces.dtos.score.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingScoreDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID underwritingScoreId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @NotNull(message = "Score value is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Score value must be non-negative")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Score value cannot exceed 1000")
    private BigDecimal scoreValue;

    @NotBlank(message = "Scorecard name is required")
    @Size(max = 100, message = "Scorecard name cannot exceed 100 characters")
    private String scorecardName;

    @Size(max = 2000, message = "Reason codes cannot exceed 2000 characters")
    private String reasonCodes; // JSON or textual reasons

    @NotNull(message = "Scoring timestamp is required")
    @PastOrPresent(message = "Scoring timestamp cannot be in the future")
    private LocalDateTime scoringTimestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}