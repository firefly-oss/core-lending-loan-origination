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
import com.firefly.core.lending.origination.core.services.UnderwritingDecisionService;
import com.firefly.core.lending.origination.interfaces.dtos.UnderwritingDecisionDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/decisions")
@RequiredArgsConstructor
@Tag(name = "UnderwritingDecision", description = "Manage underwriting decisions for a loan application")
public class UnderwritingDecisionController {

    private final UnderwritingDecisionService service;

    @GetMapping
    @Operation(summary = "List decisions", description = "Retrieves a paginated list of underwriting decisions for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<UnderwritingDecisionDTO>>> findAllDecisions(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new decision", description = "Records a new underwriting decision (APPROVED, REJECTED, etc.).")
    public Mono<ResponseEntity<UnderwritingDecisionDTO>> createDecision(
            @PathVariable UUID applicationId,
            @Valid @RequestBody UnderwritingDecisionDTO dto) {
        return service.createDecision(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{decisionId}")
    @Operation(summary = "Get a decision", description = "Fetch a specific underwriting decision record by ID.")
    public Mono<ResponseEntity<UnderwritingDecisionDTO>> getDecision(
            @PathVariable UUID applicationId,
            @PathVariable UUID decisionId) {
        return service.getDecision(applicationId, decisionId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{decisionId}")
    @Operation(summary = "Update a decision", description = "Updates an existing underwriting decision record.")
    public Mono<ResponseEntity<UnderwritingDecisionDTO>> updateDecision(
            @PathVariable UUID applicationId,
            @PathVariable UUID decisionId,
            @Valid @RequestBody UnderwritingDecisionDTO dto) {
        return service.updateDecision(applicationId, decisionId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{decisionId}")
    @Operation(summary = "Delete a decision", description = "Removes a decision record from the application.")
    public Mono<ResponseEntity<Void>> deleteDecision(
            @PathVariable UUID applicationId,
            @PathVariable UUID decisionId) {
        return service.deleteDecision(applicationId, decisionId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}