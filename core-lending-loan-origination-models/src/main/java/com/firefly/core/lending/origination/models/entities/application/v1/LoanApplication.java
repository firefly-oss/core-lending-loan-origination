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


package com.firefly.core.lending.origination.models.entities.application.v1;

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

@Table("loan_application")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    @Id
    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("application_number")
    private UUID applicationNumber;

    @Column("application_status_id")
    private UUID applicationStatusId;

    @Column("application_sub_status_id")
    private UUID applicationSubStatusId;

    @Column("application_date")
    private LocalDate applicationDate;

    @Column("submission_channel_id")
    private UUID submissionChannelId;
    
    /**
     * Identifier for a known customer who launched the application.
     * This field is null for unknown non-customers.
     */
    @Column("party_id")
    private UUID partyId;
    
    /**
     * Identifier for a known distributor who launched the application.
     * This field is null if not submitted via a distributor.
     */
    @Column("distributor_id")
    private UUID distributorId;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
