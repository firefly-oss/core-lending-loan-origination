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


package com.firefly.core.lending.origination.models.entities.integration.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("application_external_call")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationExternalCall {
    @Id
    @Column("call_id")
    private UUID callId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("service_name")
    private String serviceName; // CREDIT_BUREAU, FRAUD_CHECK, INCOME_VERIFICATION, etc.

    @Column("call_type")
    private String callType;

    @Column("request_data")
    private String requestData; // JSON

    @Column("response_data")
    private String responseData; // JSON

    @Column("status")
    private String status; // SUCCESS, FAILED, TIMEOUT

    @Column("response_time_ms")
    private Integer responseTimeMs;

    @Column("error_message")
    private String errorMessage;

    @Column("called_at")
    private LocalDateTime calledAt;

    @Column("created_at")
    private LocalDateTime createdAt;
}

