package com.catalis.core.lending.origination.core.services.score.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.lending.origination.core.mappers.score.v1.UnderwritingScoreMapper;
import com.catalis.core.lending.origination.interfaces.dtos.score.v1.UnderwritingScoreDTO;
import com.catalis.core.lending.origination.models.entities.score.v1.UnderwritingScore;
import com.catalis.core.lending.origination.models.repositories.score.v1.UnderwritingScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UnderwritingScoreServiceImpl implements UnderwritingScoreService {

    @Autowired
    private UnderwritingScoreRepository repository;

    @Autowired
    private UnderwritingScoreMapper mapper;

    @Override
    public Mono<PaginationResponse<UnderwritingScoreDTO>> findAll(Long applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<UnderwritingScoreDTO> createScore(Long applicationId, UnderwritingScoreDTO dto) {
        UnderwritingScore entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UnderwritingScoreDTO> getScore(Long applicationId, Long scoreId) {
        return repository.findById(scoreId)
                .filter(score -> score.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<UnderwritingScoreDTO> updateScore(Long applicationId, Long scoreId, UnderwritingScoreDTO dto) {
        return repository.findById(scoreId)
                .filter(score -> score.getLoanApplicationId().equals(applicationId))
                .flatMap(existingScore -> {
                    UnderwritingScore updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setUnderwritingScoreId(existingScore.getUnderwritingScoreId());
                    updatedEntity.setLoanApplicationId(applicationId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteScore(Long applicationId, Long scoreId) {
        return repository.findById(scoreId)
                .filter(score -> score.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}