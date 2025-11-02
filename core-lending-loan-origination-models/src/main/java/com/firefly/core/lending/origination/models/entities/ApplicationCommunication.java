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

import java.time.LocalDateTime;
import java.util.UUID;

@Table("application_communication")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCommunication {
    @Id
    @Column("communication_id")
    private UUID communicationId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("communication_type")
    private String communicationType; // EMAIL, SMS, PHONE, IN_PERSON, SYSTEM

    @Column("direction")
    private String direction; // INBOUND, OUTBOUND

    @Column("subject")
    private String subject;

    @Column("content")
    private String content;

    @Column("recipient")
    private String recipient;

    @Column("sender")
    private String sender;

    @Column("status")
    private String status; // SENT, DELIVERED, FAILED, READ

    @Column("sent_at")
    private LocalDateTime sentAt;

    @Column("delivered_at")
    private LocalDateTime deliveredAt;

    @Column("read_at")
    private LocalDateTime readAt;

    @Column("metadata")
    private String metadata; // JSON

    @Column("created_at")
    private LocalDateTime createdAt;
}

