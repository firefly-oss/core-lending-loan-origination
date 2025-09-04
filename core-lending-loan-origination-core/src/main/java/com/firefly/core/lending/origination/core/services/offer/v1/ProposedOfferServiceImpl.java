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


package com.firefly.core.lending.origination.core.services.offer.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.offer.v1.ProposedOfferMapper;
import com.firefly.core.lending.origination.interfaces.dtos.offer.v1.ProposedOfferDTO;
import com.firefly.core.lending.origination.models.entities.offer.v1.ProposedOffer;
import com.firefly.core.lending.origination.models.repositories.offer.v1.ProposedOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ProposedOfferServiceImpl implements ProposedOfferService {

    @Autowired
    private ProposedOfferRepository repository;

    @Autowired
    private ProposedOfferMapper mapper;

    @Override
    public Mono<PaginationResponse<ProposedOfferDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ProposedOfferDTO> createOffer(UUID applicationId, ProposedOfferDTO dto) {
        ProposedOffer entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProposedOfferDTO> getOffer(UUID applicationId, UUID offerId) {
        return repository.findById(offerId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Offer not found for the given application ID and offer ID")));
    }

    @Override
    public Mono<ProposedOfferDTO> updateOffer(UUID applicationId, UUID offerId, ProposedOfferDTO dto) {
        return repository.findById(offerId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(existingEntity -> {
                    ProposedOffer updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanApplicationId(applicationId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Offer not found for the given application ID and offer ID")));
    }

    @Override
    public Mono<Void> deleteOffer(UUID applicationId, UUID offerId) {
        return repository.findById(offerId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Offer not found for the given application ID and offer ID")))
                .flatMap(repository::delete);
    }
}