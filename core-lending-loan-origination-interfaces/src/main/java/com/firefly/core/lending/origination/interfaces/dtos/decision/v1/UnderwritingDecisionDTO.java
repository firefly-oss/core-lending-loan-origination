package com.firefly.core.lending.origination.interfaces.dtos.decision.v1;

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
public class UnderwritingDecisionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID underwritingDecisionId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @NotNull(message = "Decision date is required")
    @PastOrPresent(message = "Decision date cannot be in the future")
    private LocalDateTime decisionDate;

    @FilterableId
    @NotNull(message = "Decision code ID is required")
    private UUID decisionCodeId;

    @PositiveOrZero(message = "Approved amount must be non-negative")
    private BigDecimal approvedAmount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Approved interest rate must be non-negative")
    @DecimalMax(value = "100.0", inclusive = true, message = "Approved interest rate cannot exceed 100%")
    private BigDecimal approvedInterestRate;

    @Min(value = 1, message = "Tenor must be at least 1 month")
    @Max(value = 360, message = "Tenor cannot exceed 360 months")
    private Integer tenorMonths;

    @FilterableId
    private UUID riskGradeId;

    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}