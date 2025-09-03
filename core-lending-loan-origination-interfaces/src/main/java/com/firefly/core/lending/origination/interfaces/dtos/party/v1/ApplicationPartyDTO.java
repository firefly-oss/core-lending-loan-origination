package com.firefly.core.lending.origination.interfaces.dtos.party.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.lending.origination.interfaces.enums.party.v1.EmploymentTypeEnum;
import com.firefly.core.lending.origination.interfaces.enums.score.v1.RoleCodeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class ApplicationPartyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID applicationPartyId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotNull(message = "Role code is required")
    private RoleCodeEnum roleCode;

    @DecimalMin(value = "0.0", inclusive = true, message = "Share percentage must be non-negative")
    @DecimalMax(value = "100.0", inclusive = true, message = "Share percentage cannot exceed 100%")
    private BigDecimal sharePercentage;

    @PositiveOrZero(message = "Annual income must be non-negative")
    private BigDecimal annualIncome;

    @PositiveOrZero(message = "Monthly expenses must be non-negative")
    private BigDecimal monthlyExpenses;

    @NotNull(message = "Employment type is required")
    private EmploymentTypeEnum employmentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}