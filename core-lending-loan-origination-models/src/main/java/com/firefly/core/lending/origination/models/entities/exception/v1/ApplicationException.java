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


package com.firefly.core.lending.origination.models.entities.exception.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("application_exception")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationException {
    @Id
    @Column("exception_id")
    private UUID exceptionId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("exception_type")
    private String exceptionType; // POLICY, CREDIT, COLLATERAL, INCOME, etc.

    @Column("exception_reason")
    private String exceptionReason;

    @Column("requested_by")
    private String requestedBy;

    @Column("requested_at")
    private LocalDateTime requestedAt;

    @Column("approval_status")
    private String approvalStatus; // PENDING, APPROVED, REJECTED

    @Column("approved_by")
    private String approvedBy;

    @Column("approved_at")
    private LocalDateTime approvedAt;

    @Column("rejection_reason")
    private String rejectionReason;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

