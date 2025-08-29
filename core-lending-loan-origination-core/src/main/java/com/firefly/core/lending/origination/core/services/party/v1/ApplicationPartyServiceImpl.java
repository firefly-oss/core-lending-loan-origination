package com.firefly.core.lending.origination.core.services.party.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.party.v1.ApplicationPartyMapper;
import com.firefly.core.lending.origination.interfaces.dtos.party.v1.ApplicationPartyDTO;
import com.firefly.core.lending.origination.models.entities.party.v1.ApplicationParty;
import com.firefly.core.lending.origination.models.repositories.party.v1.ApplicationPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ApplicationPartyServiceImpl implements ApplicationPartyService {

    @Autowired
    private ApplicationPartyRepository repository;

    @Autowired
    private ApplicationPartyMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationPartyDTO>> findAll(Long applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationPartyDTO> createParty(Long applicationId, ApplicationPartyDTO dto) {
        ApplicationParty entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationPartyDTO> getParty(Long applicationId, Long partyId) {
        return repository.findById(partyId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationPartyDTO> updateParty(Long applicationId, Long partyId, ApplicationPartyDTO dto) {
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
    public Mono<Void> deleteParty(Long applicationId, Long partyId) {
        return repository.findById(partyId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(entity -> repository.delete(entity));
    }
}
