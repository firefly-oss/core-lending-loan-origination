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


package com.firefly.core.lending.origination.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.lending.origination.interfaces.enums.PaymentMethodTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanApplicationId;

    private UUID applicationNumber;

    @FilterableId
    @NotNull(message = "Application status ID is required")
    private UUID applicationStatusId;

    @FilterableId
    @NotNull(message = "Application sub-status ID is required")
    private UUID applicationSubStatusId;

    @NotNull(message = "Application date is required")
    @PastOrPresent(message = "Application date cannot be in the future")
    private LocalDate applicationDate;

    @FilterableId
    @NotNull(message = "Submission channel ID is required")
    private UUID submissionChannelId;

    /**
     * Identifier for a known distributor who launched the application.
     * This field is null if not submitted via a distributor.
     */
    @FilterableId
    private UUID distributorId;

    /**
     * Identifier for the distributor agency associated with the application.
     * This field is null if not submitted via a distributor agency.
     */
    @FilterableId
    private UUID distributorAgencyId;

    /**
     * Identifier for the distributor agent associated with the application.
     * This field is null if not submitted via a distributor agent.
     */
    @FilterableId
    private UUID distributorAgentId;

    private String businessUnit;

    @FilterableId
    private UUID branchId;

    private String loanPurpose;
    private String loanPurposeDescription;
    private LocalDateTime submittedAt;
    private LocalDateTime firstReviewAt;
    private LocalDate decisionDueDate;
    private LocalDate expectedClosingDate;

    @FilterableId
    private UUID loanOfficerId;

    private LocalDateTime assignedAt;
    private Boolean isRush;
    private Boolean isException;
    private Boolean requiresManualReview;
    private String externalReferenceNumber;
    private String sourceSystem;

    /**
     * Method type for loan disbursement.
     * - INTERNAL_ACCOUNT: Disburse to an account within the Firefly core banking system
     * - EXTERNAL_ACCOUNT: Disburse to an external bank account (stored in application_external_bank_account)
     */
    private PaymentMethodTypeEnum disbursementMethodType;

    /**
     * Internal account ID for disbursement (ONLY when disbursementMethodType = INTERNAL_ACCOUNT).
     * References an account in the Firefly core banking system.
     * Must be null when disbursementMethodType = EXTERNAL_ACCOUNT.
     */
    @FilterableId
    private UUID disbursementInternalAccountId;

    /**
     * External bank account ID for disbursement (ONLY when disbursementMethodType = EXTERNAL_ACCOUNT).
     * References application_external_bank_account table.
     * Must be null when disbursementMethodType = INTERNAL_ACCOUNT.
     */
    @FilterableId
    private UUID disbursementExternalBankAccountId;

    /**
     * Method type for loan payment collection.
     * - INTERNAL_ACCOUNT: Collect payments from an account within the Firefly core banking system
     * - EXTERNAL_ACCOUNT: Collect payments via direct debit/domiciliación from external bank account
     */
    private PaymentMethodTypeEnum paymentCollectionMethodType;

    /**
     * Internal account ID for payment collection (ONLY when paymentCollectionMethodType = INTERNAL_ACCOUNT).
     * References an account in the Firefly core banking system from which installments will be debited.
     * Must be null when paymentCollectionMethodType = EXTERNAL_ACCOUNT.
     */
    @FilterableId
    private UUID paymentCollectionInternalAccountId;

    /**
     * External bank account ID for payment collection (ONLY when paymentCollectionMethodType = EXTERNAL_ACCOUNT).
     * References application_external_bank_account table.
     * Used for setting up direct debit/domiciliación.
     * Must be null when paymentCollectionMethodType = INTERNAL_ACCOUNT.
     */
    @FilterableId
    private UUID paymentCollectionExternalBankAccountId;

    @Size(max = 1000, message = "Note cannot exceed 1000 characters")
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}