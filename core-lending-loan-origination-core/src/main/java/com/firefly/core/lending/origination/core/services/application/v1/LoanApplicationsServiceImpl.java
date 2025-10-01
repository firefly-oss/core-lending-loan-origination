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


package com.firefly.core.lending.origination.core.services.application.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.application.v1.LoanApplicationMapper;
import com.firefly.core.lending.origination.interfaces.dtos.application.v1.LoanApplicationDTO;
import com.firefly.core.lending.origination.models.entities.application.v1.LoanApplication;
import com.firefly.core.lending.origination.models.repositories.application.v1.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class LoanApplicationsServiceImpl implements LoanApplicationsService {

    @Autowired
    private LoanApplicationRepository repository;

    @Autowired
    private LoanApplicationMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanApplicationDTO>> filterLoanApplications(FilterRequest<LoanApplicationDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        LoanApplication.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaginationResponse<LoanApplicationDTO>> listLoanApplications(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<LoanApplicationDTO> createLoanApplication(LoanApplicationDTO dto) {
        return Mono.just(dto)
                .map(loanAppDto -> {
                    // Auto-generate UUID for applicationNumber at service level
                    loanAppDto.setApplicationNumber(UUID.randomUUID());
                    return loanAppDto;
                })
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanApplicationDTO> getLoanApplication(UUID applicationId) {
        return repository.findById(applicationId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanApplicationDTO> updateLoanApplication(UUID applicationId, LoanApplicationDTO dto) {
        return repository.findById(applicationId)
                .flatMap(existing -> {
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLoanApplication(UUID applicationId) {
        return repository.findById(applicationId)
                .flatMap(repository::delete);
    }
}