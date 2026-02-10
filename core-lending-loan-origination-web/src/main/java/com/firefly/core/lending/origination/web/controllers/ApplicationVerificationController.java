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
import com.firefly.core.lending.origination.core.services.ApplicationVerificationService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationVerificationDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/verifications")
@RequiredArgsConstructor
@Tag(name = "ApplicationVerification", description = "Manage verifications for a loan application")
public class ApplicationVerificationController {

    private final ApplicationVerificationService service;

    @GetMapping
    @Operation(summary = "List verifications", description = "Retrieves a paginated list of verifications for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationVerificationDTO>>> findAllVerifications(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a verification", description = "Adds a new verification record for the application.")
    public Mono<ResponseEntity<ApplicationVerificationDTO>> createVerification(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationVerificationDTO dto) {
        return service.createVerification(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{verificationId}")
    @Operation(summary = "Get a verification", description = "Fetch a specific verification record by ID.")
    public Mono<ResponseEntity<ApplicationVerificationDTO>> getVerification(
            @PathVariable UUID applicationId,
            @PathVariable UUID verificationId) {
        return service.getVerification(applicationId, verificationId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{verificationId}")
    @Operation(summary = "Update a verification", description = "Updates an existing verification record.")
    public Mono<ResponseEntity<ApplicationVerificationDTO>> updateVerification(
            @PathVariable UUID applicationId,
            @PathVariable UUID verificationId,
            @Valid @RequestBody ApplicationVerificationDTO dto) {
        return service.updateVerification(applicationId, verificationId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{verificationId}")
    @Operation(summary = "Delete a verification", description = "Removes a verification record from the application.")
    public Mono<ResponseEntity<Void>> deleteVerification(
            @PathVariable UUID applicationId,
            @PathVariable UUID verificationId) {
        return service.deleteVerification(applicationId, verificationId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

