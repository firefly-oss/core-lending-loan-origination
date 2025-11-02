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


package com.firefly.core.lending.origination.web.controllers;

import com.firefly.core.lending.origination.core.services.DocumentTypeService;
import com.firefly.core.lending.origination.models.entities.DocumentType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/document-types")
@RequiredArgsConstructor
@Tag(name = "DocumentType", description = "Manage document types")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @GetMapping
    @Operation(summary = "List all document types", description = "Retrieves all available document types.")
    public Flux<DocumentType> getAllDocumentTypes() {
        return documentTypeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get document type by ID", description = "Retrieves a specific document type by its ID.")
    public Mono<DocumentType> getDocumentTypeById(@PathVariable UUID id) {
        return documentTypeService.findById(id);
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get document type by code", description = "Retrieves a specific document type by its code.")
    public Mono<DocumentType> getDocumentTypeByCode(@PathVariable String code) {
        return documentTypeService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create document type", description = "Creates a new document type.")
    public Mono<DocumentType> createDocumentType(@Valid @RequestBody DocumentType documentType) {
        return documentTypeService.save(documentType);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update document type", description = "Updates an existing document type.")
    public Mono<DocumentType> updateDocumentType(@PathVariable UUID id, @Valid @RequestBody DocumentType documentType) {
        documentType.setDocumentTypeId(id);
        return documentTypeService.save(documentType);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete document type", description = "Deletes a document type.")
    public Mono<Void> deleteDocumentType(@PathVariable UUID id) {
        return documentTypeService.deleteById(id);
    }
}