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

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.ApplicationStatusMapper;
import com.firefly.core.lending.origination.core.services.ApplicationStatusService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationStatusDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationStatus;
import com.firefly.core.lending.origination.models.repositories.ApplicationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

    @Autowired
    private ApplicationStatusRepository repository;

    @Autowired
    private ApplicationStatusMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationStatusDTO>> filterApplicationStatuses(FilterRequest<ApplicationStatusDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        ApplicationStatus.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaginationResponse<ApplicationStatusDTO>> listApplicationStatuses(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationStatusDTO> createApplicationStatus(ApplicationStatusDTO dto) {
        return Mono.just(dto)
                .map(statusDto -> {
                    // Set audit fields for creation
                    statusDto.setCreatedAt(LocalDateTime.now());
                    statusDto.setUpdatedAt(LocalDateTime.now());
                    return statusDto;
                })
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationStatusDTO> getApplicationStatus(UUID applicationStatusId) {
        return repository.findById(applicationStatusId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationStatusDTO> updateApplicationStatus(UUID applicationStatusId, ApplicationStatusDTO dto) {
        return repository.findById(applicationStatusId)
                .flatMap(existing -> {
                    ApplicationStatus updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setApplicationStatusId(existing.getApplicationStatusId());
                    updatedEntity.setCreatedAt(existing.getCreatedAt());
                    updatedEntity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteApplicationStatus(UUID applicationStatusId) {
        return repository.findById(applicationStatusId)
                .flatMap(repository::delete);
    }

    @Override
    public Mono<ApplicationStatusDTO> findByCode(String code) {
        return repository.findByCode(code)
                .map(mapper::toDTO);
    }
}