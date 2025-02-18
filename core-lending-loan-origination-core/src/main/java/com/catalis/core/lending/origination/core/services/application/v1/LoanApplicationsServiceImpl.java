package com.catalis.core.lending.origination.core.services.application.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.lending.origination.core.mappers.application.v1.LoanApplicationMapper;
import com.catalis.core.lending.origination.interfaces.dtos.application.v1.LoanApplicationDTO;
import com.catalis.core.lending.origination.models.entities.application.v1.LoanApplication;
import com.catalis.core.lending.origination.models.repositories.application.v1.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

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
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanApplicationDTO> getLoanApplication(Long applicationId) {
        return repository.findById(applicationId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LoanApplicationDTO> updateLoanApplication(Long applicationId, LoanApplicationDTO dto) {
        return repository.findById(applicationId)
                .flatMap(existing -> {
                    LoanApplication updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLoanApplicationId(existing.getLoanApplicationId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLoanApplication(Long applicationId) {
        return repository.findById(applicationId)
                .flatMap(repository::delete);
    }
}