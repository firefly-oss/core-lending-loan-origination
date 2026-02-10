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
import com.firefly.core.lending.origination.core.mappers.UnderwritingScoreMapper;
import com.firefly.core.lending.origination.core.services.UnderwritingScoreService;
import com.firefly.core.lending.origination.interfaces.dtos.UnderwritingScoreDTO;
import com.firefly.core.lending.origination.models.entities.UnderwritingScore;
import com.firefly.core.lending.origination.models.repositories.UnderwritingScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class UnderwritingScoreServiceImpl implements UnderwritingScoreService {

    @Autowired
    private UnderwritingScoreRepository repository;

    @Autowired
    private UnderwritingScoreMapper mapper;

    @Override
    public Mono<PaginationResponse<UnderwritingScoreDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<UnderwritingScoreDTO> createScore(UUID applicationId, UnderwritingScoreDTO dto) {
        UnderwritingScore entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UnderwritingScoreDTO> getScore(UUID applicationId, UUID scoreId) {
        return repository.findById(scoreId)
                .filter(score -> score.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UnderwritingScoreDTO> updateScore(UUID applicationId, UUID scoreId, UnderwritingScoreDTO dto) {
        return repository.findById(scoreId)
                .filter(score -> score.getLoanApplicationId().equals(applicationId))
                .flatMap(existingScore -> {
                    UnderwritingScore updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setUnderwritingScoreId(existingScore.getUnderwritingScoreId());
                    updatedEntity.setLoanApplicationId(applicationId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteScore(UUID applicationId, UUID scoreId) {
        return repository.findById(scoreId)
                .filter(score -> score.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}