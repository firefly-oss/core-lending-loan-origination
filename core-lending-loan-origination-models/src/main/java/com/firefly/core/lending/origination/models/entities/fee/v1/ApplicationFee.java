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


package com.firefly.core.lending.origination.models.entities.fee.v1;

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

@Table("application_fee")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFee {
    @Id
    @Column("fee_id")
    private UUID feeId;

    @Column("loan_application_id")
    private UUID loanApplicationId;

    @Column("fee_type")
    private String feeType; // ORIGINATION, PROCESSING, APPRAISAL, INSURANCE, etc.

    @Column("fee_name")
    private String feeName;

    @Column("fee_amount")
    private BigDecimal feeAmount;

    @Column("fee_percentage")
    private BigDecimal feePercentage;

    @Column("is_refundable")
    private Boolean isRefundable;

    @Column("is_financed")
    private Boolean isFinanced; // Whether the fee is added to the loan amount

    @Column("payment_status")
    private String paymentStatus; // PENDING, PAID, WAIVED, REFUNDED

    @Column("paid_at")
    private LocalDateTime paidAt;

    @Column("waived_reason")
    private String waivedReason;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

