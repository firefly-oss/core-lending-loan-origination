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


package com.firefly.core.lending.origination.core.services.bankaccount.v1;

import com.firefly.common.core.queries.PagedResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.bankaccount.v1.ApplicationExternalBankAccountMapper;
import com.firefly.core.lending.origination.interfaces.dtos.bankaccount.v1.ApplicationExternalBankAccountDTO;
import com.firefly.core.lending.origination.models.entities.bankaccount.v1.ApplicationExternalBankAccount;
import com.firefly.core.lending.origination.models.repositories.bankaccount.v1.ApplicationExternalBankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    public Mono<PagedResponse<ApplicationExternalBankAccountDTO>> findAll(UUID loanApplicationId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        
        return repository.findByLoanApplicationId(loanApplicationId)
                .map(mapper::toDTO)
                .collectList()
                .zipWith(repository.findByLoanApplicationId(loanApplicationId).count())
                .map(tuple -> PaginationUtils.createPagedResponse(
                        tuple.getT1(),
                        page,
                        size,
                        tuple.getT2()
                ));
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

