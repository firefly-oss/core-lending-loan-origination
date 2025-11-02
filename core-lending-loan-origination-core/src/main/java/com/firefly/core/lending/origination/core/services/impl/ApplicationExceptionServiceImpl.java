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


package com.firefly.core.lending.origination.core.services.exception.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.ApplicationExceptionMapper;
import com.firefly.core.lending.origination.interfaces.dtos.exception.v1.ApplicationExceptionDTO;
import com.firefly.core.lending.origination.models.entities.exception.v1.ApplicationException;
import com.firefly.core.lending.origination.models.repositories.exception.v1.ApplicationExceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationExceptionServiceImpl implements ApplicationExceptionService {

    @Autowired
    private ApplicationExceptionRepository repository;

    @Autowired
    private ApplicationExceptionMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationExceptionDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationExceptionDTO> createException(UUID applicationId, ApplicationExceptionDTO dto) {
        ApplicationException entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationExceptionDTO> getException(UUID applicationId, UUID exceptionId) {
        return repository.findById(exceptionId)
                .filter(exception -> exception.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationExceptionDTO> updateException(UUID applicationId, UUID exceptionId, ApplicationExceptionDTO dto) {
        return repository.findById(exceptionId)
                .filter(exception -> exception.getLoanApplicationId().equals(applicationId))
                .flatMap(existingException -> {
                    ApplicationException updated = mapper.toEntity(dto);
                    updated.setExceptionId(exceptionId);
                    updated.setLoanApplicationId(applicationId);
                    updated.setCreatedAt(existingException.getCreatedAt());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteException(UUID applicationId, UUID exceptionId) {
        return repository.findById(exceptionId)
                .filter(exception -> exception.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}

