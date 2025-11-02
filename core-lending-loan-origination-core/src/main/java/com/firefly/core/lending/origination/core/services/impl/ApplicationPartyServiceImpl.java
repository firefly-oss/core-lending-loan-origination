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
import com.firefly.core.lending.origination.core.mappers.ApplicationPartyMapper;
import com.firefly.core.lending.origination.core.services.ApplicationPartyService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationPartyDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationParty;
import com.firefly.core.lending.origination.models.repositories.ApplicationPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationPartyServiceImpl implements ApplicationPartyService {

    @Autowired
    private ApplicationPartyRepository repository;

    @Autowired
    private ApplicationPartyMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationPartyDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationPartyDTO> createParty(UUID applicationId, ApplicationPartyDTO dto) {
        ApplicationParty entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationPartyDTO> getParty(UUID applicationId, UUID partyId) {
        return repository.findById(partyId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationPartyDTO> updateParty(UUID applicationId, UUID partyId, ApplicationPartyDTO dto) {
        return repository.findById(partyId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(existing -> {
                    ApplicationParty updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanApplicationId(applicationId);
                    updatedEntity.setApplicationPartyId(existing.getApplicationPartyId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteParty(UUID applicationId, UUID partyId) {
        return repository.findById(partyId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(entity -> repository.delete(entity));
    }
}
