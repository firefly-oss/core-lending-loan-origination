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

import com.firefly.core.lending.origination.core.services.DocumentTypeService;
import com.firefly.core.lending.origination.models.entities.DocumentType;
import com.firefly.core.lending.origination.models.repositories.DocumentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    @Override
    public Flux<DocumentType> findAll() {
        return documentTypeRepository.findAll();
    }

    @Override
    public Mono<DocumentType> findById(UUID id) {
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
    public Mono<Void> deleteById(UUID id) {
        return documentTypeRepository.deleteById(id);
    }
}