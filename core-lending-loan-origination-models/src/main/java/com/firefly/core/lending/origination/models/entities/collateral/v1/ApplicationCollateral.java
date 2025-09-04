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


package com.firefly.core.lending.origination.models.entities.collateral.v1;

import com.firefly.core.lending.origination.interfaces.enums.collateral.v1.CollateralTypeEnum;
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

@Table("application_collateral")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCollateral {
    @Id
    @Column("application_collateral_id")
    private UUID applicationCollateralId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("collateral_type")
    private CollateralTypeEnum collateralType;

    @Column("estimated_value")
    private BigDecimal estimatedValue;

    @Column("ownership_details")
    private String ownershipDetails;

    @Column("is_primary_collateral")
    private Boolean isPrimaryCollateral;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}