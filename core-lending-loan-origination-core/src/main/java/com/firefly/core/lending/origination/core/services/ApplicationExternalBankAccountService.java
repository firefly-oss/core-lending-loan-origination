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


package com.firefly.core.lending.origination.core.services;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationExternalBankAccountDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service interface for managing external bank accounts associated with loan applications.
 */
public interface ApplicationExternalBankAccountService {

    /**
     * Find all external bank accounts for a specific loan application with pagination.
     */
    Mono<PaginationResponse<ApplicationExternalBankAccountDTO>> findAll(UUID loanApplicationId, PaginationRequest paginationRequest);
    
    /**
     * Create a new external bank account.
     */
    Mono<ApplicationExternalBankAccountDTO> create(ApplicationExternalBankAccountDTO dto);
    
    /**
     * Get an external bank account by ID.
     */
    Mono<ApplicationExternalBankAccountDTO> get(UUID loanApplicationId, UUID externalBankAccountId);
    
    /**
     * Update an existing external bank account.
     */
    Mono<ApplicationExternalBankAccountDTO> update(UUID loanApplicationId, UUID externalBankAccountId, ApplicationExternalBankAccountDTO dto);
    
    /**
     * Delete an external bank account.
     */
    Mono<Void> delete(UUID loanApplicationId, UUID externalBankAccountId);
}

