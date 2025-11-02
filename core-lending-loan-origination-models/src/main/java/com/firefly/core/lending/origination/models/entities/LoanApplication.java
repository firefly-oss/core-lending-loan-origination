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

import com.firefly.core.lending.origination.interfaces.enums.PaymentMethodTypeEnum;
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
     * Identifier for a known distributor who launched the application.
     * This field is null if not submitted via a distributor.
     */
    @Column("distributor_id")
    private UUID distributorId;

    /**
     * Identifier for the distributor agency associated with the application.
     * This field is null if not submitted via a distributor agency.
     */
    @Column("distributor_agency_id")
    private UUID distributorAgencyId;

    /**
     * Identifier for the distributor agent associated with the application.
     * This field is null if not submitted via a distributor agent.
     */
    @Column("distributor_agent_id")
    private UUID distributorAgentId;

    /**
     * Business unit responsible for this application.
     */
    @Column("business_unit")
    private String businessUnit;

    /**
     * Branch where the application was originated.
     */
    @Column("branch_id")
    private UUID branchId;

    /**
     * Purpose of the loan.
     */
    @Column("loan_purpose")
    private String loanPurpose;

    /**
     * Detailed description of the loan purpose.
     */
    @Column("loan_purpose_description")
    private String loanPurposeDescription;

    /**
     * Timestamp when the application was submitted.
     */
    @Column("submitted_at")
    private LocalDateTime submittedAt;

    /**
     * Timestamp of the first review.
     */
    @Column("first_review_at")
    private LocalDateTime firstReviewAt;

    /**
     * Due date for making a decision on this application.
     */
    @Column("decision_due_date")
    private LocalDate decisionDueDate;

    /**
     * Expected closing date for the loan.
     */
    @Column("expected_closing_date")
    private LocalDate expectedClosingDate;

    /**
     * Loan officer assigned to this application.
     */
    @Column("loan_officer_id")
    private UUID loanOfficerId;

    /**
     * Timestamp when the loan officer was assigned.
     */
    @Column("assigned_at")
    private LocalDateTime assignedAt;

    /**
     * Flag indicating if this is a rush/urgent application.
     */
    @Column("is_rush")
    private Boolean isRush;

    /**
     * Flag indicating if this application has exceptions.
     */
    @Column("is_exception")
    private Boolean isException;

    /**
     * Flag indicating if manual review is required.
     */
    @Column("requires_manual_review")
    private Boolean requiresManualReview;

    /**
     * External reference number from another system.
     */
    @Column("external_reference_number")
    private String externalReferenceNumber;

    /**
     * Source system that originated this application.
     */
    @Column("source_system")
    private String sourceSystem;

    /**
     * Method type for loan disbursement.
     * - INTERNAL_ACCOUNT: Disburse to an account within the Firefly core banking system
     * - EXTERNAL_ACCOUNT: Disburse to an external bank account (stored in application_external_bank_account)
     */
    @Column("disbursement_method_type")
    private PaymentMethodTypeEnum disbursementMethodType;

    /**
     * Internal account ID for disbursement (ONLY when disbursementMethodType = INTERNAL_ACCOUNT).
     * References an account in the Firefly core banking system.
     * Must be null when disbursementMethodType = EXTERNAL_ACCOUNT.
     */
    @Column("disbursement_internal_account_id")
    private UUID disbursementInternalAccountId;

    /**
     * External bank account ID for disbursement (ONLY when disbursementMethodType = EXTERNAL_ACCOUNT).
     * References application_external_bank_account table.
     * Must be null when disbursementMethodType = INTERNAL_ACCOUNT.
     */
    @Column("disbursement_external_bank_account_id")
    private UUID disbursementExternalBankAccountId;

    /**
     * Method type for loan payment collection.
     * - INTERNAL_ACCOUNT: Collect payments from an account within the Firefly core banking system
     * - EXTERNAL_ACCOUNT: Collect payments via direct debit/domiciliación from external bank account
     */
    @Column("payment_collection_method_type")
    private PaymentMethodTypeEnum paymentCollectionMethodType;

    /**
     * Internal account ID for payment collection (ONLY when paymentCollectionMethodType = INTERNAL_ACCOUNT).
     * References an account in the Firefly core banking system from which installments will be debited.
     * Must be null when paymentCollectionMethodType = EXTERNAL_ACCOUNT.
     */
    @Column("payment_collection_internal_account_id")
    private UUID paymentCollectionInternalAccountId;

    /**
     * External bank account ID for payment collection (ONLY when paymentCollectionMethodType = EXTERNAL_ACCOUNT).
     * References application_external_bank_account table.
     * Used for setting up direct debit/domiciliación.
     * Must be null when paymentCollectionMethodType = INTERNAL_ACCOUNT.
     */
    @Column("payment_collection_external_bank_account_id")
    private UUID paymentCollectionExternalBankAccountId;

    @Column("note")
    private String note;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
