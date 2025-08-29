package com.firefly.core.lending.origination.core.services.decision.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.decision.v1.UnderwritingDecisionMapper;
import com.firefly.core.lending.origination.interfaces.dtos.decision.v1.UnderwritingDecisionDTO;
import com.firefly.core.lending.origination.models.entities.decision.v1.UnderwritingDecision;
import com.firefly.core.lending.origination.models.repositories.decision.v1.UnderwritingDecisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UnderwritingDecisionServiceImpl implements UnderwritingDecisionService {

    @Autowired
    private UnderwritingDecisionRepository repository;

    @Autowired
    private UnderwritingDecisionMapper mapper;

    @Override
    public Mono<PaginationResponse<UnderwritingDecisionDTO>> findAll(Long applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<UnderwritingDecisionDTO> createDecision(Long applicationId, UnderwritingDecisionDTO dto) {
        dto.setLoanApplicationId(applicationId);
        UnderwritingDecision entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UnderwritingDecisionDTO> getDecision(Long applicationId, Long decisionId) {
        return repository.findById(decisionId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UnderwritingDecisionDTO> updateDecision(Long applicationId, Long decisionId, UnderwritingDecisionDTO dto) {
        return repository.findById(decisionId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(existingEntity -> {
                    UnderwritingDecision updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setUnderwritingDecisionId(decisionId);
                    updatedEntity.setLoanApplicationId(applicationId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDecision(Long applicationId, Long decisionId) {
        return repository.findById(decisionId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}
