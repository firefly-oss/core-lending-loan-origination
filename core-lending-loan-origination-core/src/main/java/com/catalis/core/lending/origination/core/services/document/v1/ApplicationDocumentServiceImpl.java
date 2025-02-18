package com.catalis.core.lending.origination.core.services.document.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.lending.origination.core.mappers.document.v1.ApplicationDocumentMapper;
import com.catalis.core.lending.origination.interfaces.dtos.document.v1.ApplicationDocumentDTO;
import com.catalis.core.lending.origination.models.entities.document.v1.ApplicationDocument;
import com.catalis.core.lending.origination.models.repositories.document.v1.ApplicationDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ApplicationDocumentServiceImpl implements ApplicationDocumentService {

    @Autowired
    private ApplicationDocumentRepository repository;

    @Autowired
    private ApplicationDocumentMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationDocumentDTO>> findAll(Long applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationDocumentDTO> createDocument(Long applicationId, ApplicationDocumentDTO dto) {
        ApplicationDocument entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationDocumentDTO> getDocument(Long applicationId, Long documentId) {
        return repository.findById(documentId)
                .filter(document -> document.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationDocumentDTO> updateDocument(Long applicationId, Long documentId, ApplicationDocumentDTO dto) {
        return repository.findById(documentId)
                .filter(document -> document.getLoanApplicationId().equals(applicationId))
                .flatMap(existingDocument -> {
                    existingDocument.setDocumentType(dto.getDocumentType());
                    existingDocument.setIsMandatory(dto.getIsMandatory());
                    existingDocument.setIsReceived(dto.getIsReceived());
                    existingDocument.setReceivedAt(dto.getReceivedAt());
                    return repository.save(existingDocument);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDocument(Long applicationId, Long documentId) {
        return repository.findById(documentId)
                .filter(document -> document.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}
