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
import com.firefly.core.lending.origination.core.mappers.ApplicationCommunicationMapper;
import com.firefly.core.lending.origination.core.services.ApplicationCommunicationService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationCommunicationDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationCommunication;
import com.firefly.core.lending.origination.models.repositories.ApplicationCommunicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationCommunicationServiceImpl implements ApplicationCommunicationService {

    @Autowired
    private ApplicationCommunicationRepository repository;

    @Autowired
    private ApplicationCommunicationMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationCommunicationDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationCommunicationDTO> createCommunication(UUID applicationId, ApplicationCommunicationDTO dto) {
        ApplicationCommunication entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationCommunicationDTO> getCommunication(UUID applicationId, UUID communicationId) {
        return repository.findById(communicationId)
                .filter(communication -> communication.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationCommunicationDTO> updateCommunication(UUID applicationId, UUID communicationId, ApplicationCommunicationDTO dto) {
        return repository.findById(communicationId)
                .filter(communication -> communication.getLoanApplicationId().equals(applicationId))
                .flatMap(existingCommunication -> {
                    ApplicationCommunication updated = mapper.toEntity(dto);
                    updated.setCommunicationId(communicationId);
                    updated.setLoanApplicationId(applicationId);
                    updated.setCreatedAt(existingCommunication.getCreatedAt());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCommunication(UUID applicationId, UUID communicationId) {
        return repository.findById(communicationId)
                .filter(communication -> communication.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}

