package com.catalis.core.lending.origination.core.services.offer.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.lending.origination.core.mappers.offer.v1.ProposedOfferMapper;
import com.catalis.core.lending.origination.interfaces.dtos.offer.v1.ProposedOfferDTO;
import com.catalis.core.lending.origination.models.entities.offer.v1.ProposedOffer;
import com.catalis.core.lending.origination.models.repositories.offer.v1.ProposedOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProposedOfferServiceImpl implements ProposedOfferService {

    @Autowired
    private ProposedOfferRepository repository;

    @Autowired
    private ProposedOfferMapper mapper;

    @Override
    public Mono<PaginationResponse<ProposedOfferDTO>> findAll(Long applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ProposedOfferDTO> createOffer(Long applicationId, ProposedOfferDTO dto) {
        ProposedOffer entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProposedOfferDTO> getOffer(Long applicationId, Long offerId) {
        return repository.findById(offerId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Offer not found for the given application ID and offer ID")));
    }

    @Override
    public Mono<ProposedOfferDTO> updateOffer(Long applicationId, Long offerId, ProposedOfferDTO dto) {
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
    public Mono<Void> deleteOffer(Long applicationId, Long offerId) {
        return repository.findById(offerId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Offer not found for the given application ID and offer ID")))
                .flatMap(repository::delete);
    }
}