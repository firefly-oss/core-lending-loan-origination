/*
 * Copyright 2025 Firefly Software Foundation
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
import com.firefly.core.lending.origination.core.mappers.ApplicationPartyMapper;
import com.firefly.core.lending.origination.core.services.ApplicationPartyService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationPartyDTO;
import com.firefly.core.lending.origination.interfaces.dtos.EmploymentDataPatchDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationParty;
import com.firefly.core.lending.origination.models.repositories.ApplicationPartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireflyframework.web.error.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationPartyServiceImpl implements ApplicationPartyService {

    private final ApplicationPartyRepository repository;
    private final ApplicationPartyMapper mapper;

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

    @Override
    public Mono<ApplicationPartyDTO> updateEmploymentData(UUID applicationPartyId, EmploymentDataPatchDTO patch) {
        log.debug("Patching employment data for applicationPartyId={}", applicationPartyId);
        return repository.findById(applicationPartyId)
                .switchIfEmpty(Mono.error(new BusinessException(
                        HttpStatus.NOT_FOUND,
                        "APPLICATION_PARTY_NOT_FOUND",
                        "Application party not found: " + applicationPartyId)))
                .flatMap(existing -> {
                    applyEmploymentDataPatch(existing, patch);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existing);
                })
                .doOnNext(saved -> log.info("Employment data updated for applicationPartyId={}",
                        saved.getApplicationPartyId()))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationPartyDTO> findPrimaryByApplicationId(UUID applicationId) {
        log.debug("Looking up primary application party for applicationId={}", applicationId);
        return repository.findFirstByLoanApplicationIdAndIsPrimaryTrue(applicationId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<ApplicationPartyDTO> findByPartyId(UUID partyId) {
        log.debug("Looking up application parties for partyId={}", partyId);
        return repository.findByPartyId(partyId)
                .map(mapper::toDTO);
    }

    /**
     * Applies the (non-null) values of the patch onto the entity.
     * Null fields on the patch are ignored — every other column on the entity
     * is left untouched (per BE-4 contract).
     */
    private static void applyEmploymentDataPatch(ApplicationParty entity, EmploymentDataPatchDTO patch) {
        if (patch == null) {
            return;
        }
        if (patch.getEmploymentStatus() != null) {
            entity.setEmploymentStatus(patch.getEmploymentStatus());
        }
        if (patch.getEmploymentTypeLabel() != null) {
            entity.setEmploymentTypeLabel(patch.getEmploymentTypeLabel());
        }
        if (patch.getEmployer() != null) {
            entity.setEmployer(patch.getEmployer());
        }
        if (patch.getPosition() != null) {
            entity.setPosition(patch.getPosition());
        }
        if (patch.getEmploymentStartDate() != null) {
            entity.setEmploymentStartDate(patch.getEmploymentStartDate());
        }
        if (patch.getAnnualPaydays() != null) {
            entity.setAnnualPaydays(patch.getAnnualPaydays());
        }
        if (patch.getMonthlySalary() != null) {
            entity.setMonthlySalary(patch.getMonthlySalary());
        }
        if (patch.getHousingType() != null) {
            entity.setHousingType(patch.getHousingType());
        }
        if (patch.getHousingCost() != null) {
            entity.setHousingCost(patch.getHousingCost());
        }
        if (patch.getHousingStartDate() != null) {
            entity.setHousingStartDate(patch.getHousingStartDate());
        }
        if (patch.getExistingLoans() != null) {
            entity.setExistingLoans(patch.getExistingLoans());
        }
        if (patch.getOtherDebts() != null) {
            entity.setOtherDebts(patch.getOtherDebts());
        }
    }
}
