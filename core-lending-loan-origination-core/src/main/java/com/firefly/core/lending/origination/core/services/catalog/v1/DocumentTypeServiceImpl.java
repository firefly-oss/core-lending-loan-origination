package com.firefly.core.lending.origination.core.services.catalog.v1;

import com.firefly.core.lending.origination.models.entities.catalog.v1.DocumentType;
import com.firefly.core.lending.origination.models.repositories.catalog.v1.DocumentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    @Override
    public Flux<DocumentType> findAll() {
        return documentTypeRepository.findAll();
    }

    @Override
    public Mono<DocumentType> findById(Long id) {
        return documentTypeRepository.findById(id);
    }

    @Override
    public Mono<DocumentType> findByCode(String code) {
        return documentTypeRepository.findByCode(code);
    }

    @Override
    public Mono<DocumentType> save(DocumentType documentType) {
        LocalDateTime now = LocalDateTime.now();
        
        if (documentType.getDocumentTypeId() == null) {
            documentType.setCreatedAt(now);
        }
        
        documentType.setUpdatedAt(now);
        
        return documentTypeRepository.save(documentType);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return documentTypeRepository.deleteById(id);
    }
}