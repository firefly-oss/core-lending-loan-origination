package com.firefly.core.lending.origination.core.services.status.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.status.v1.LoanApplicationStatusHistoryMapper;
import com.firefly.core.lending.origination.interfaces.dtos.status.v1.LoanApplicationStatusHistoryDTO;
import com.firefly.core.lending.origination.models.entities.status.v1.LoanApplicationStatusHistory;
import com.firefly.core.lending.origination.models.repositories.status.v1.LoanApplicationStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class LoanApplicationStatusHistoryServiceImpl implements LoanApplicationStatusHistoryService {

    @Autowired
    private LoanApplicationStatusHistoryRepository repository;

    @Autowired
    private LoanApplicationStatusHistoryMapper mapper;

    @Override
    public Mono<PaginationResponse<LoanApplicationStatusHistoryDTO>> findAll(Long applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<LoanApplicationStatusHistoryDTO> createStatusHistory(Long applicationId, LoanApplicationStatusHistoryDTO dto) {
        LoanApplicationStatusHistory entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanApplicationStatusHistoryDTO> getStatusHistory(Long applicationId, Long statusHistoryId) {
        return repository.findById(statusHistoryId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanApplicationStatusHistoryDTO> updateStatusHistory(Long applicationId, Long statusHistoryId, LoanApplicationStatusHistoryDTO dto) {
        return repository.findById(statusHistoryId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(existingEntity -> {
                    LoanApplicationStatusHistory updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanApplicationId(existingEntity.getLoanApplicationId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteStatusHistory(Long applicationId, Long statusHistoryId) {
        return repository.findById(statusHistoryId)
                .filter(entity -> entity.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}
