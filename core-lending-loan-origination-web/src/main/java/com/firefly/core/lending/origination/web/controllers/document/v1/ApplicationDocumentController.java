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


package com.firefly.core.lending.origination.web.controllers.document.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.document.v1.ApplicationDocumentService;
import com.firefly.core.lending.origination.interfaces.dtos.document.v1.ApplicationDocumentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-applications/{applicationId}/documents")
@RequiredArgsConstructor
@Tag(name = "ApplicationDocument", description = "Manage documents for a loan application")
public class ApplicationDocumentController {

    private final ApplicationDocumentService service;

    @GetMapping
    @Operation(summary = "List documents", description = "Retrieves a paginated list of documents for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationDocumentDTO>>> findAllDocuments(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a document record", description = "Adds a new document record for the application.")
    public Mono<ResponseEntity<ApplicationDocumentDTO>> createDocument(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationDocumentDTO dto) {
        return service.createDocument(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{documentId}")
    @Operation(summary = "Get a document", description = "Fetch a specific document record by ID.")
    public Mono<ResponseEntity<ApplicationDocumentDTO>> getDocument(
            @PathVariable UUID applicationId,
            @PathVariable UUID documentId) {
        return service.getDocument(applicationId, documentId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{documentId}")
    @Operation(summary = "Update a document record", description = "Updates metadata for an existing document.")
    public Mono<ResponseEntity<ApplicationDocumentDTO>> updateDocument(
            @PathVariable UUID applicationId,
            @PathVariable UUID documentId,
            @Valid @RequestBody ApplicationDocumentDTO dto) {
        return service.updateDocument(applicationId, documentId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{documentId}")
    @Operation(summary = "Delete a document record", description = "Removes a document record from the application.")
    public Mono<ResponseEntity<Void>> deleteDocument(
            @PathVariable UUID applicationId,
            @PathVariable UUID documentId) {
        return service.deleteDocument(applicationId, documentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
