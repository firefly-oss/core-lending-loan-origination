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


package com.firefly.core.lending.origination.interfaces.dtos.collateral.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.lending.origination.interfaces.enums.collateral.v1.CollateralTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
public class ApplicationCollateralDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID applicationCollateralId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @NotNull(message = "Collateral type is required")
    private CollateralTypeEnum collateralType;

    @PositiveOrZero(message = "Estimated value must be non-negative")
    private BigDecimal estimatedValue;

    @Size(max = 500, message = "Ownership details cannot exceed 500 characters")
    private String ownershipDetails;

    @NotNull(message = "Primary collateral flag is required")
    private Boolean isPrimaryCollateral;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}