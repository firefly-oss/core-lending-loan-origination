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
import com.firefly.core.lending.origination.interfaces.enums.AccountUsageTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for EXTERNAL bank account associated with a loan application.
 * This represents bank accounts OUTSIDE the Firefly core banking system.
 * 
 * <p>For internal accounts (within Firefly), use the internalAccountId fields
 * in LoanApplicationDTO directly.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationExternalBankAccountDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID externalBankAccountId;

    @FilterableId
    @NotNull(message = "Loan application ID is required")
    private UUID loanApplicationId;

    @NotNull(message = "Account usage type is required")
    private AccountUsageTypeEnum accountUsageType;

    @NotNull(message = "Account holder name is required")
    @Size(max = 200, message = "Account holder name cannot exceed 200 characters")
    private String accountHolderName;

    @NotNull(message = "Bank name is required")
    @Size(max = 200, message = "Bank name cannot exceed 200 characters")
    private String bankName;

    @Size(max = 50, message = "Bank code cannot exceed 50 characters")
    private String bankCode;

    @Size(max = 50, message = "Branch code cannot exceed 50 characters")
    private String branchCode;

    @NotNull(message = "Account number is required")
    @Size(max = 50, message = "Account number cannot exceed 50 characters")
    private String accountNumber;

    @Size(max = 50, message = "Account type cannot exceed 50 characters")
    private String accountType;

    @Size(max = 3, message = "Currency code must be 3 characters")
    private String currencyCode;

    @Size(max = 34, message = "IBAN cannot exceed 34 characters")
    private String iban;

    @Size(max = 50, message = "Routing number cannot exceed 50 characters")
    private String routingNumber;

    private Boolean isVerified;

    @Size(max = 50, message = "Verification method cannot exceed 50 characters")
    private String verificationMethod;

    private LocalDateTime verifiedAt;

    private Boolean isPrimary;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}

