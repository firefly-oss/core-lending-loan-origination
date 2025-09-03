package com.firefly.core.lending.origination.core.services.catalog.v1;

import com.firefly.core.lending.origination.models.entities.catalog.v1.DocumentType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DocumentTypeService {
    Flux<DocumentType> findAll();
    Mono<DocumentType> findById(UUID id);
    Mono<DocumentType> findByCode(String code);
    Mono<DocumentType> save(DocumentType documentType);
    Mono<Void> deleteById(UUID id);
}