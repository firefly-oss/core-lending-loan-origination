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
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data transfer object for a lending simulation.
 *
 * <p>Calculation values ({@code monthlyPayment}, {@code tin}, {@code tae},
 * {@code totalAmount}) are typically populated by {@code domain-core-pricing-engine}
 * after the simulation row has been created.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A persisted lending simulation (PERSONAL_LOAN or LEASING).")
public class SimulationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Unique identifier of the simulation.", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID simulationId;

    @FilterableId
    @Schema(description = "Reference to the product in core-common-product-mgmt. No FK across services.")
    private UUID productId;

    @NotNull(message = "Product type is required")
    @Size(max = 50)
    @Schema(description = "Product family discriminator.", example = "PERSONAL_LOAN")
    private String productType;

    @NotNull(message = "Requested amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Requested amount must be positive")
    @Schema(description = "Requested loan / financed amount.", example = "10000.00")
    private BigDecimal requestedAmount;

    @NotNull(message = "Term is required")
    @Positive(message = "Term must be positive")
    @Schema(description = "Number of monthly installments.", example = "36")
    private Integer term;

    @Size(max = 50)
    @Schema(description = "Loan purpose (free-form code).", example = "VEHICLE_PURCHASE")
    private String purpose;

    @Size(max = 50)
    @Schema(description = "Customer business sector (LEASING).")
    private String sector;

    @Size(max = 50)
    @Schema(description = "Asset type to be financed (LEASING).")
    private String assetType;

    @Schema(description = "Computed monthly payment. Populated by the pricing engine.")
    private BigDecimal monthlyPayment;

    @Schema(description = "Nominal interest rate (TIN). Populated by the pricing engine.")
    private BigDecimal tin;

    @Schema(description = "Annual equivalent rate (TAE). Populated by the pricing engine.")
    private BigDecimal tae;

    @Schema(description = "Total amount to be repaid (principal + interest). Populated by the pricing engine.")
    private BigDecimal totalAmount;

    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    @Schema(description = "ISO 4217 currency code.", example = "EUR", defaultValue = "EUR")
    private String currency;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
}
