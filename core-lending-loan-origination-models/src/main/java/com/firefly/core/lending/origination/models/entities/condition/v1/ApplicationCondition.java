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


package com.firefly.core.lending.origination.models.entities.condition.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("application_condition")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCondition {
    @Id
    @Column("condition_id")
    private UUID conditionId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("condition_type")
    private String conditionType; // PRE_APPROVAL, POST_APPROVAL, CLOSING

    @Column("condition_category")
    private String conditionCategory; // DOCUMENT, VERIFICATION, LEGAL, FINANCIAL

    @Column("description")
    private String description;

    @Column("is_mandatory")
    private Boolean isMandatory;

    @Column("status")
    private String status; // PENDING, IN_PROGRESS, SATISFIED, WAIVED, EXPIRED

    @Column("due_date")
    private LocalDate dueDate;

    @Column("satisfied_date")
    private LocalDateTime satisfiedDate;

    @Column("satisfied_by")
    private String satisfiedBy;

    @Column("waived_reason")
    private String waivedReason;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

