package com.catalis.core.lending.origination.core.services.catalog.v1;

import com.catalis.core.lending.origination.models.entities.catalog.v1.DocumentType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentTypeService {
    Flux<DocumentType> findAll();
    Mono<DocumentType> findById(Long id);
    Mono<DocumentType> findByCode(String code);
    Mono<DocumentType> save(DocumentType documentType);
    Mono<Void> deleteById(Long id);
}