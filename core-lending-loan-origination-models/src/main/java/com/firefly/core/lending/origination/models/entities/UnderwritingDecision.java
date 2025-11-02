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

@Table("underwriting_decision")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingDecision {
    @Id
    @Column("underwriting_decision_id")
    private UUID underwritingDecisionId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("decision_date")
    private LocalDateTime decisionDate;

    @Column("decision_code_id")
    private UUID decisionCodeId;
    
    private DecisionCode decisionCode;

    @Column("approved_amount")
    private BigDecimal approvedAmount;

    @Column("approved_interest_rate")
    private BigDecimal approvedInterestRate;

    @Column("tenor_months")
    private Integer tenorMonths;

    @Column("risk_grade_id")
    private UUID riskGradeId;
    
    private RiskGrade riskGrade;

    @Column("remarks")
    private String remarks;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}