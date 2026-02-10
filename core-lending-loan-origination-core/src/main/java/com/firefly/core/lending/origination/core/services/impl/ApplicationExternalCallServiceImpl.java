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

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.ApplicationExternalCallMapper;
import com.firefly.core.lending.origination.core.services.ApplicationExternalCallService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationExternalCallDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationExternalCall;
import com.firefly.core.lending.origination.models.repositories.ApplicationExternalCallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationExternalCallServiceImpl implements ApplicationExternalCallService {

    @Autowired
    private ApplicationExternalCallRepository repository;

    @Autowired
    private ApplicationExternalCallMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationExternalCallDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationExternalCallDTO> createExternalCall(UUID applicationId, ApplicationExternalCallDTO dto) {
        ApplicationExternalCall entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationExternalCallDTO> getExternalCall(UUID applicationId, UUID callId) {
        return repository.findById(callId)
                .filter(call -> call.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationExternalCallDTO> updateExternalCall(UUID applicationId, UUID callId, ApplicationExternalCallDTO dto) {
        return repository.findById(callId)
                .filter(call -> call.getLoanApplicationId().equals(applicationId))
                .flatMap(existingCall -> {
                    ApplicationExternalCall updated = mapper.toEntity(dto);
                    updated.setCallId(callId);
                    updated.setLoanApplicationId(applicationId);
                    updated.setCreatedAt(existingCall.getCreatedAt());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteExternalCall(UUID applicationId, UUID callId) {
        return repository.findById(callId)
                .filter(call -> call.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}

