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


package com.firefly.core.lending.origination.core.services.collateral.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.collateral.v1.ApplicationCollateralMapper;
import com.firefly.core.lending.origination.interfaces.dtos.collateral.v1.ApplicationCollateralDTO;
import com.firefly.core.lending.origination.models.entities.collateral.v1.ApplicationCollateral;
import com.firefly.core.lending.origination.models.repositories.collateral.v1.ApplicationCollateralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationCollateralServiceImpl implements ApplicationCollateralService {

    @Autowired
    private ApplicationCollateralRepository repository;

    @Autowired
    private ApplicationCollateralMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationCollateralDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationCollateralDTO> createCollateral(UUID applicationId, ApplicationCollateralDTO dto) {
        dto.setLoanApplicationId(applicationId);
        ApplicationCollateral entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationCollateralDTO> getCollateral(UUID applicationId, UUID collateralId) {
        return repository.findById(collateralId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationCollateralDTO> updateCollateral(UUID applicationId, UUID collateralId, ApplicationCollateralDTO dto) {
        return repository.findById(collateralId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(existingEntity -> {
                    dto.setLoanApplicationId(applicationId);
                    dto.setApplicationCollateralId(collateralId);
                    ApplicationCollateral updatedEntity = mapper.toEntity(dto);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCollateral(UUID applicationId, UUID collateralId) {
        return repository.findById(collateralId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}
