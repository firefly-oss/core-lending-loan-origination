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
import com.firefly.core.lending.origination.core.mappers.ApplicationMetricMapper;
import com.firefly.core.lending.origination.core.services.ApplicationMetricService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationMetricDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationMetric;
import com.firefly.core.lending.origination.models.repositories.ApplicationMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationMetricServiceImpl implements ApplicationMetricService {

    @Autowired
    private ApplicationMetricRepository repository;

    @Autowired
    private ApplicationMetricMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationMetricDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationMetricDTO> createMetric(UUID applicationId, ApplicationMetricDTO dto) {
        ApplicationMetric entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationMetricDTO> getMetric(UUID applicationId, UUID metricId) {
        return repository.findById(metricId)
                .filter(metric -> metric.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationMetricDTO> updateMetric(UUID applicationId, UUID metricId, ApplicationMetricDTO dto) {
        return repository.findById(metricId)
                .filter(metric -> metric.getLoanApplicationId().equals(applicationId))
                .flatMap(existingMetric -> {
                    ApplicationMetric updated = mapper.toEntity(dto);
                    updated.setMetricId(metricId);
                    updated.setLoanApplicationId(applicationId);
                    updated.setCreatedAt(existingMetric.getCreatedAt());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteMetric(UUID applicationId, UUID metricId) {
        return repository.findById(metricId)
                .filter(metric -> metric.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}

