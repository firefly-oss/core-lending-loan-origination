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


package com.firefly.core.lending.origination.models.repositories.bankaccount.v1;

import com.firefly.core.lending.origination.models.entities.enums.AccountUsageTypeEnum;
import com.firefly.core.lending.origination.models.entities.ApplicationExternalBankAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository for managing external bank accounts associated with loan applications.
 */
@Repository
public interface ApplicationExternalBankAccountRepository extends ReactiveCrudRepository<ApplicationExternalBankAccount, UUID> {
    
    /**
     * Find all external bank accounts with pagination.
     */
    Flux<ApplicationExternalBankAccount> findAllBy(Pageable pageable);
    
    /**
     * Find all external bank accounts for a specific loan application.
     */
    Flux<ApplicationExternalBankAccount> findByLoanApplicationId(UUID loanApplicationId);
    
    /**
     * Find external bank accounts by loan application and usage type.
     */
    Flux<ApplicationExternalBankAccount> findByLoanApplicationIdAndAccountUsageType(
            UUID loanApplicationId, 
            AccountUsageTypeEnum accountUsageType
    );
    
    /**
     * Find the primary external bank account for a loan application.
     */
    Mono<ApplicationExternalBankAccount> findByLoanApplicationIdAndIsPrimary(
            UUID loanApplicationId, 
            Boolean isPrimary
    );
}

