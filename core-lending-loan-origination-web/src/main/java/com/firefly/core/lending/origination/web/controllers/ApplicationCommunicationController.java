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

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.ApplicationCommunicationService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationCommunicationDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/communications")
@RequiredArgsConstructor
@Tag(name = "ApplicationCommunication", description = "Manage communications for a loan application")
public class ApplicationCommunicationController {

    private final ApplicationCommunicationService service;

    @GetMapping
    @Operation(summary = "List communications", description = "Retrieves a paginated list of communications for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationCommunicationDTO>>> findAllCommunications(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a communication", description = "Adds a new communication record for the application.")
    public Mono<ResponseEntity<ApplicationCommunicationDTO>> createCommunication(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationCommunicationDTO dto) {
        return service.createCommunication(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{communicationId}")
    @Operation(summary = "Get a communication", description = "Fetch a specific communication record by ID.")
    public Mono<ResponseEntity<ApplicationCommunicationDTO>> getCommunication(
            @PathVariable UUID applicationId,
            @PathVariable UUID communicationId) {
        return service.getCommunication(applicationId, communicationId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{communicationId}")
    @Operation(summary = "Update a communication", description = "Updates an existing communication record.")
    public Mono<ResponseEntity<ApplicationCommunicationDTO>> updateCommunication(
            @PathVariable UUID applicationId,
            @PathVariable UUID communicationId,
            @Valid @RequestBody ApplicationCommunicationDTO dto) {
        return service.updateCommunication(applicationId, communicationId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{communicationId}")
    @Operation(summary = "Delete a communication", description = "Removes a communication record from the application.")
    public Mono<ResponseEntity<Void>> deleteCommunication(
            @PathVariable UUID applicationId,
            @PathVariable UUID communicationId) {
        return service.deleteCommunication(applicationId, communicationId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

