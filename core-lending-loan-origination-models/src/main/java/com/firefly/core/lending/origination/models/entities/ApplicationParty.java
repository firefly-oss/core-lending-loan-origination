/*
 * Copyright 2025 Firefly Software Foundation
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
import java.time.LocalDate;
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

    // -----------------------------------------------------------------
    // BE-4 economic / employment data (V15)
    // -----------------------------------------------------------------

    @Column("employment_status")
    private String employmentStatus;

    /**
     * Free-form employment type label sent by the front-end.
     * Distinct from the catalog FK column {@link #employmentTypeId}.
     */
    @Column("employment_type_label")
    private String employmentTypeLabel;

    @Column("employer")
    private String employer;

    @Column("position")
    private String position;

    @Column("employment_start_date")
    private LocalDate employmentStartDate;

    @Column("annual_paydays")
    private Short annualPaydays;

    @Column("monthly_salary")
    private BigDecimal monthlySalary;

    @Column("housing_type")
    private String housingType;

    @Column("housing_cost")
    private BigDecimal housingCost;

    @Column("housing_start_date")
    private LocalDate housingStartDate;

    @Column("existing_loans")
    private Short existingLoans;

    @Column("other_debts")
    private BigDecimal otherDebts;

    /**
     * TRUE if this party is the primary applicant for the loan application.
     * Enforced unique per loan_application_id via partial unique index
     * {@code ux_application_party_primary}.
     */
    @Column("is_primary")
    private Boolean isPrimary;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
