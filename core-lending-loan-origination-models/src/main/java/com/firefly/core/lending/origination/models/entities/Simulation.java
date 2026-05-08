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


package com.firefly.core.lending.origination.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Persisted lending simulation. Calculation is performed in
 * {@code domain-core-pricing-engine}; this entity only stores the result.
 */
@Table("simulation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Simulation {

    @Id
    @Column("simulation_id")
    private UUID simulationId;

    /**
     * Reference to a product in core-common-product-mgmt.
     * No FK because the product service is in a different bounded context.
     */
    @Column("product_id")
    private UUID productId;

    /**
     * Product family discriminator (e.g. PERSONAL_LOAN, LEASING).
     */
    @Column("product_type")
    private String productType;

    @Column("requested_amount")
    private BigDecimal requestedAmount;

    @Column("term")
    private Integer term;

    @Column("purpose")
    private String purpose;

    @Column("sector")
    private String sector;

    @Column("asset_type")
    private String assetType;

    @Column("monthly_payment")
    private BigDecimal monthlyPayment;

    /**
     * Nominal interest rate (Tasa de Interes Nominal).
     */
    @Column("tin")
    private BigDecimal tin;

    /**
     * Annual equivalent rate (Tasa Anual Equivalente).
     */
    @Column("tae")
    private BigDecimal tae;

    @Column("total_amount")
    private BigDecimal totalAmount;

    @Column("currency")
    private String currency;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
