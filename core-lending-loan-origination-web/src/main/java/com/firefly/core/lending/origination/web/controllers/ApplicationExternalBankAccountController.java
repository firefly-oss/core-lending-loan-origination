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


package com.firefly.core.lending.origination.web.controllers.bankaccount.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.ApplicationExternalBankAccountService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationExternalBankAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST controller for managing external bank accounts associated with loan applications.
 *
 * <p>This controller handles EXTERNAL bank accounts (accounts outside the Firefly core banking system)
 * used for loan disbursement and/or repayment via direct debit/domiciliación.</p>
 *
 * <p>For internal accounts (within Firefly), use the payment method fields in LoanApplication directly.</p>
 */
@RestController
@RequestMapping("/api/v1/loan-applications/{applicationId}/external-bank-accounts")
@RequiredArgsConstructor
@Tag(name = "Application External Bank Accounts", description = "Manage external bank accounts for loan applications")
public class ApplicationExternalBankAccountController {

    private final ApplicationExternalBankAccountService service;

    @GetMapping
    @Operation(summary = "List all external bank accounts for a loan application")
    public Mono<PaginationResponse<ApplicationExternalBankAccountDTO>> findAll(
            @PathVariable UUID applicationId,
            PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new external bank account")
    public Mono<ApplicationExternalBankAccountDTO> create(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationExternalBankAccountDTO dto) {
        dto.setLoanApplicationId(applicationId);
        return service.create(dto);
    }
    
    @GetMapping("/{externalBankAccountId}")
    @Operation(summary = "Get an external bank account by ID")
    public Mono<ApplicationExternalBankAccountDTO> get(
            @PathVariable UUID applicationId,
            @PathVariable UUID externalBankAccountId) {
        return service.get(applicationId, externalBankAccountId);
    }
    
    @PutMapping("/{externalBankAccountId}")
    @Operation(summary = "Update an external bank account")
    public Mono<ApplicationExternalBankAccountDTO> update(
            @PathVariable UUID applicationId,
            @PathVariable UUID externalBankAccountId,
            @Valid @RequestBody ApplicationExternalBankAccountDTO dto) {
        return service.update(applicationId, externalBankAccountId, dto);
    }
    
    @DeleteMapping("/{externalBankAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an external bank account")
    public Mono<Void> delete(
            @PathVariable UUID applicationId,
            @PathVariable UUID externalBankAccountId) {
        return service.delete(applicationId, externalBankAccountId);
    }
}

