/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.origination.core.services.impl;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.lending.origination.core.mappers.ApplicationDocumentMapper;
import com.firefly.core.lending.origination.core.services.ApplicationDocumentService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationDocumentDTO;
import com.firefly.core.lending.origination.models.entities.ApplicationDocument;
import com.firefly.core.lending.origination.models.repositories.ApplicationDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ApplicationDocumentServiceImpl implements ApplicationDocumentService {

    @Autowired
    private ApplicationDocumentRepository repository;

    @Autowired
    private ApplicationDocumentMapper mapper;

    @Override
    public Mono<PaginationResponse<ApplicationDocumentDTO>> findAll(UUID applicationId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ApplicationDocumentDTO> createDocument(UUID applicationId, ApplicationDocumentDTO dto) {
        ApplicationDocument entity = mapper.toEntity(dto);
        entity.setLoanApplicationId(applicationId);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationDocumentDTO> getDocument(UUID applicationId, UUID documentId) {
        return repository.findById(documentId)
                .filter(document -> document.getLoanApplicationId().equals(applicationId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ApplicationDocumentDTO> updateDocument(UUID applicationId, UUID documentId, ApplicationDocumentDTO dto) {
        return repository.findById(documentId)
                .filter(document -> document.getLoanApplicationId().equals(applicationId))
                .flatMap(existingDocument -> {
                    existingDocument.setDocumentId(dto.getDocumentId());
                    existingDocument.setIsMandatory(dto.getIsMandatory());
                    existingDocument.setIsReceived(dto.getIsReceived());
                    existingDocument.setReceivedAt(dto.getReceivedAt());
                    return repository.save(existingDocument);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDocument(UUID applicationId, UUID documentId) {
        return repository.findById(documentId)
                .filter(document -> document.getLoanApplicationId().equals(applicationId))
                .flatMap(repository::delete);
    }
}
