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


package com.firefly.core.lending.origination.web.controllers.integration.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.integration.v1.ApplicationExternalCallService;
import com.firefly.core.lending.origination.interfaces.dtos.integration.v1.ApplicationExternalCallDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/external-calls")
@RequiredArgsConstructor
@Tag(name = "ApplicationExternalCall", description = "Manage external calls for a loan application")
public class ApplicationExternalCallController {

    private final ApplicationExternalCallService service;

    @GetMapping
    @Operation(summary = "List external calls", description = "Retrieves a paginated list of external calls for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationExternalCallDTO>>> findAllExternalCalls(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add an external call", description = "Adds a new external call record for the application.")
    public Mono<ResponseEntity<ApplicationExternalCallDTO>> createExternalCall(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationExternalCallDTO dto) {
        return service.createExternalCall(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{callId}")
    @Operation(summary = "Get an external call", description = "Fetch a specific external call record by ID.")
    public Mono<ResponseEntity<ApplicationExternalCallDTO>> getExternalCall(
            @PathVariable UUID applicationId,
            @PathVariable UUID callId) {
        return service.getExternalCall(applicationId, callId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{callId}")
    @Operation(summary = "Update an external call", description = "Updates an existing external call record.")
    public Mono<ResponseEntity<ApplicationExternalCallDTO>> updateExternalCall(
            @PathVariable UUID applicationId,
            @PathVariable UUID callId,
            @Valid @RequestBody ApplicationExternalCallDTO dto) {
        return service.updateExternalCall(applicationId, callId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{callId}")
    @Operation(summary = "Delete an external call", description = "Removes an external call record from the application.")
    public Mono<ResponseEntity<Void>> deleteExternalCall(
            @PathVariable UUID applicationId,
            @PathVariable UUID callId) {
        return service.deleteExternalCall(applicationId, callId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

