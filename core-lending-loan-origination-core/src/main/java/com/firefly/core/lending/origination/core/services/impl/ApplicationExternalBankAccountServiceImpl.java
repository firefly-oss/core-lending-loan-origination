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


package com.firefly.core.lending.origination.core.services.impl;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.ApplicationExternalBankAccountMapper;
import com.firefly.core.lending.origination.core.services.ApplicationExternalBankAccountService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationExternalBankAccountDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationExternalBankAccount;
import com.firefly.core.lending.origination.models.repositories.ApplicationExternalBankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of ApplicationExternalBankAccountService.
 */
@Service
@RequiredArgsConstructor
public class ApplicationExternalBankAccountServiceImpl implements ApplicationExternalBankAccountService {

    private final ApplicationExternalBankAccountRepository repository;
    private final ApplicationExternalBankAccountMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationExternalBankAccountDTO>> findAll(UUID loanApplicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByLoanApplicationId(loanApplicationId),
                () -> repository.findByLoanApplicationId(loanApplicationId).count()
        );
    }
    
    @Override
    public Mono<ApplicationExternalBankAccountDTO> create(ApplicationExternalBankAccountDTO dto) {
        ApplicationExternalBankAccount entity = mapper.toEntity(dto);
        entity.setExternalBankAccountId(UUID.randomUUID());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        
        return repository.save(entity)
                .map(mapper::toDTO);
    }
    
    @Override
    public Mono<ApplicationExternalBankAccountDTO> get(UUID loanApplicationId, UUID externalBankAccountId) {
        return repository.findById(externalBankAccountId)
                .filter(entity -> entity.getLoanApplicationId().equals(loanApplicationId))
                .map(mapper::toDTO);
    }
    
    @Override
    public Mono<ApplicationExternalBankAccountDTO> update(UUID loanApplicationId, UUID externalBankAccountId, ApplicationExternalBankAccountDTO dto) {
        return repository.findById(externalBankAccountId)
                .filter(entity -> entity.getLoanApplicationId().equals(loanApplicationId))
                .flatMap(existing -> {
                    ApplicationExternalBankAccount updated = mapper.toEntity(dto);
                    updated.setExternalBankAccountId(externalBankAccountId);
                    updated.setLoanApplicationId(loanApplicationId);
                    updated.setCreatedAt(existing.getCreatedAt());
                    updated.setUpdatedAt(LocalDateTime.now());
                    
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }
    
    @Override
    public Mono<Void> delete(UUID loanApplicationId, UUID externalBankAccountId) {
        return repository.findById(externalBankAccountId)
                .filter(entity -> entity.getLoanApplicationId().equals(loanApplicationId))
                .flatMap(repository::delete);
    }
}

