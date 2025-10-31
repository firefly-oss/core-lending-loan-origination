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


package com.firefly.core.lending.origination.core.services.fee.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.fee.v1.ApplicationFeeMapper;
import com.firefly.core.lending.origination.interfaces.dtos.fee.v1.ApplicationFeeDTO;
import com.firefly.core.lending.origination.models.entities.fee.v1.ApplicationFee;
import com.firefly.core.lending.origination.models.repositories.fee.v1.ApplicationFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationFeeServiceImpl implements ApplicationFeeService {

    @Autowired
    private ApplicationFeeRepository repository;

    @Autowired
    private ApplicationFeeMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationFeeDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationFeeDTO> createFee(UUID applicationId, ApplicationFeeDTO dto) {
        ApplicationFee entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationFeeDTO> getFee(UUID applicationId, UUID feeId) {
        return repository.findById(feeId)
                .filter(fee -> fee.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationFeeDTO> updateFee(UUID applicationId, UUID feeId, ApplicationFeeDTO dto) {
        return repository.findById(feeId)
                .filter(fee -> fee.getLoanApplicationId().equals(applicationId))
                .flatMap(existingFee -> {
                    ApplicationFee updated = mapper.toEntity(dto);
                    updated.setFeeId(feeId);
                    updated.setLoanApplicationId(applicationId);
                    updated.setCreatedAt(existingFee.getCreatedAt());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteFee(UUID applicationId, UUID feeId) {
        return repository.findById(feeId)
                .filter(fee -> fee.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}

