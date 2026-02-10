/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.origination.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fireflyframework.utils.annotations.FilterableId;
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

    @FilterableId
    @NotNull(message = "Role code ID is required")
    private UUID roleCodeId;

    @DecimalMin(value = "0.0", inclusive = true, message = "Share percentage must be non-negative")
    @DecimalMax(value = "100.0", inclusive = true, message = "Share percentage cannot exceed 100%")
    private BigDecimal sharePercentage;

    @PositiveOrZero(message = "Annual income must be non-negative")
    private BigDecimal annualIncome;

    @PositiveOrZero(message = "Monthly expenses must be non-negative")
    private BigDecimal monthlyExpenses;

    @FilterableId
    @NotNull(message = "Employment type ID is required")
    private UUID employmentTypeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}