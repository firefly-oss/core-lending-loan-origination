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

import com.firefly.core.lending.origination.interfaces.enums.AccountUsageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an EXTERNAL bank account associated with a loan application.
 * This entity stores details of bank accounts OUTSIDE the Firefly core banking system.
 *
 * <p>Used for:</p>
 * <ul>
 *   <li>Disbursement: Sending approved loan funds to the customer's external account</li>
 *   <li>Payment Collection: Setting up direct debit/domiciliación from customer's external account</li>
 * </ul>
 *
 * <p><b>Note:</b> For internal accounts (accounts within the Firefly core banking system),
 * use the internalAccountId fields in LoanApplication directly. This entity is ONLY for
 * external bank accounts.</p>
 */
@Table("application_external_bank_account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationExternalBankAccount {
    @Id
    @Column("external_bank_account_id")
    private UUID externalBankAccountId;

    /**
     * Reference to the loan application.
     */
    @Column("loan_application_id")
    private UUID loanApplicationId;

    /**
     * Type of account usage.
     * Values: DISBURSEMENT, PAYMENT_COLLECTION, BOTH
     */
    @Column("account_usage_type")
    private AccountUsageTypeEnum accountUsageType;

    /**
     * Account holder name.
     */
    @Column("account_holder_name")
    private String accountHolderName;

    /**
     * Bank name.
     */
    @Column("bank_name")
    private String bankName;

    /**
     * Bank code or identifier (e.g., BIC, SWIFT code).
     */
    @Column("bank_code")
    private String bankCode;

    /**
     * Branch code or identifier.
     */
    @Column("branch_code")
    private String branchCode;

    /**
     * Account number.
     */
    @Column("account_number")
    private String accountNumber;

    /**
     * Account type.
     * Values: CHECKING, SAVINGS, etc.
     */
    @Column("account_type")
    private String accountType;

    /**
     * Currency code (ISO 4217).
     */
    @Column("currency_code")
    private String currencyCode;

    /**
     * IBAN (International Bank Account Number) if applicable.
     */
    @Column("iban")
    private String iban;

    /**
     * Routing number (for US banks) or sort code (for UK banks).
     */
    @Column("routing_number")
    private String routingNumber;

    /**
     * Flag indicating if this account has been verified.
     */
    @Column("is_verified")
    private Boolean isVerified;

    /**
     * Verification method used.
     * Values: MICRO_DEPOSIT, INSTANT_VERIFICATION, MANUAL, etc.
     */
    @Column("verification_method")
    private String verificationMethod;

    /**
     * Timestamp when the account was verified.
     */
    @Column("verified_at")
    private LocalDateTime verifiedAt;

    /**
     * Flag indicating if this is the primary account.
     */
    @Column("is_primary")
    private Boolean isPrimary;

    /**
     * Additional notes or comments.
     */
    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

