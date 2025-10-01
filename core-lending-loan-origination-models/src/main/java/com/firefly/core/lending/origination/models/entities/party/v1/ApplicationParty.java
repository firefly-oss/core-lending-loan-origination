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


package com.firefly.core.lending.origination.models.entities.party.v1;

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

@Table("application_party")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationParty {
    @Id
    @Column("application_party_id")
    private UUID applicationPartyId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("party_id")
    private UUID partyId;

    @Column("role_code_id")
    private UUID roleCodeId;

    @Column("share_percentage")
    private BigDecimal sharePercentage;

    @Column("annual_income")
    private BigDecimal annualIncome;

    @Column("monthly_expenses")
    private BigDecimal monthlyExpenses;

    @Column("employment_type_id")
    private UUID employmentTypeId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
