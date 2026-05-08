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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request payload for {@code PATCH /api/v1/simulations/{id}}.
 *
 * <p>Used by {@code domain-core-pricing-engine} (or the lending domain layer)
 * to write back the computed financial values onto an existing simulation row.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Computed financial values to persist on an existing simulation.")
public class UpdateSimulationCalculationsRequest {

    @NotNull(message = "monthlyPayment is required")
    @Schema(description = "Computed monthly payment.", example = "320.55")
    private BigDecimal monthlyPayment;

    @NotNull(message = "tin is required")
    @Schema(description = "Nominal interest rate (TIN) as a percentage.", example = "7.9900")
    private BigDecimal tin;

    @NotNull(message = "tae is required")
    @Schema(description = "Annual equivalent rate (TAE) as a percentage.", example = "8.2900")
    private BigDecimal tae;

    @NotNull(message = "totalAmount is required")
    @Schema(description = "Total amount to be repaid (principal + interest).", example = "11539.80")
    private BigDecimal totalAmount;
}
