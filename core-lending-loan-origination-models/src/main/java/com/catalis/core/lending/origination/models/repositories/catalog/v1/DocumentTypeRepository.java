package com.catalis.core.lending.origination.models.repositories.catalog.v1;

import com.catalis.core.lending.origination.models.entities.catalog.v1.DocumentType;
import com.catalis.core.lending.origination.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DocumentTypeRepository extends BaseRepository<DocumentType, Long> {
    Mono<DocumentType> findByCode(String code);
}