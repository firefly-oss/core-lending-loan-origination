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
import com.firefly.core.lending.origination.core.mappers.UnderwritingDecisionMapper;
import com.firefly.core.lending.origination.core.services.UnderwritingDecisionService;
import com.firefly.core.lending.origination.interfaces.dtos.UnderwritingDecisionDTO;
import com.firefly.core.lending.origination.models.entities.UnderwritingDecision;
import com.firefly.core.lending.origination.models.repositories.UnderwritingDecisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class UnderwritingDecisionServiceImpl implements UnderwritingDecisionService {

    @Autowired
    private UnderwritingDecisionRepository repository;

    @Autowired
    private UnderwritingDecisionMapper mapper;

    @Override
    public Mono<PaginationResponse<UnderwritingDecisionDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<UnderwritingDecisionDTO> createDecision(UUID applicationId, UnderwritingDecisionDTO dto) {
        dto.setLoanApplicationId(applicationId);
        UnderwritingDecision entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UnderwritingDecisionDTO> getDecision(UUID applicationId, UUID decisionId) {
        return repository.findById(decisionId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UnderwritingDecisionDTO> updateDecision(UUID applicationId, UUID decisionId, UnderwritingDecisionDTO dto) {
        return repository.findById(decisionId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(existingEntity -> {
                    UnderwritingDecision updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setUnderwritingDecisionId(decisionId);
                    updatedEntity.setLoanApplicationId(applicationId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDecision(UUID applicationId, UUID decisionId) {
        return repository.findById(decisionId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}
