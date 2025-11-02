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


package com.firefly.core.lending.origination.web.controllers.exception.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.ApplicationExceptionService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationExceptionDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/exceptions")
@RequiredArgsConstructor
@Tag(name = "ApplicationException", description = "Manage exceptions for a loan application")
public class ApplicationExceptionController {

    private final ApplicationExceptionService service;

    @GetMapping
    @Operation(summary = "List exceptions", description = "Retrieves a paginated list of exceptions for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationExceptionDTO>>> findAllExceptions(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add an exception", description = "Adds a new exception record for the application.")
    public Mono<ResponseEntity<ApplicationExceptionDTO>> createException(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationExceptionDTO dto) {
        return service.createException(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{exceptionId}")
    @Operation(summary = "Get an exception", description = "Fetch a specific exception record by ID.")
    public Mono<ResponseEntity<ApplicationExceptionDTO>> getException(
            @PathVariable UUID applicationId,
            @PathVariable UUID exceptionId) {
        return service.getException(applicationId, exceptionId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{exceptionId}")
    @Operation(summary = "Update an exception", description = "Updates an existing exception record.")
    public Mono<ResponseEntity<ApplicationExceptionDTO>> updateException(
            @PathVariable UUID applicationId,
            @PathVariable UUID exceptionId,
            @Valid @RequestBody ApplicationExceptionDTO dto) {
        return service.updateException(applicationId, exceptionId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{exceptionId}")
    @Operation(summary = "Delete an exception", description = "Removes an exception record from the application.")
    public Mono<ResponseEntity<Void>> deleteException(
            @PathVariable UUID applicationId,
            @PathVariable UUID exceptionId) {
        return service.deleteException(applicationId, exceptionId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

