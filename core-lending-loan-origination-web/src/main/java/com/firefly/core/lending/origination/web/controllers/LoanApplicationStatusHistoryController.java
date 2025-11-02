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

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.LoanApplicationStatusHistoryService;
import com.firefly.core.lending.origination.interfaces.dtos.LoanApplicationStatusHistoryDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/status-history")
@RequiredArgsConstructor
@Tag(name = "LoanApplicationStatusHistory", description = "Manage status history records for a loan application")
public class LoanApplicationStatusHistoryController {

    private final LoanApplicationStatusHistoryService service;

    @GetMapping
    @Operation(summary = "List status history", description = "Retrieves a paginated list of status history entries for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<LoanApplicationStatusHistoryDTO>>> findAllStatusHistory(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a status record", description = "Creates a new status history record for a loan application.")
    public Mono<ResponseEntity<LoanApplicationStatusHistoryDTO>> createStatusHistory(
            @PathVariable UUID applicationId,
            @Valid @RequestBody LoanApplicationStatusHistoryDTO dto) {
        return service.createStatusHistory(applicationId, dto)
                .map(createdDto -> ResponseEntity.status(201).body(createdDto))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{statusHistoryId}")
    @Operation(summary = "Get a status record", description = "Retrieves a specific status history record.")
    public Mono<ResponseEntity<LoanApplicationStatusHistoryDTO>> getStatusHistory(
            @PathVariable UUID applicationId,
            @PathVariable UUID statusHistoryId) {
        return service.getStatusHistory(applicationId, statusHistoryId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{statusHistoryId}")
    @Operation(summary = "Update a status record", description = "Updates an existing status history record.")
    public Mono<ResponseEntity<LoanApplicationStatusHistoryDTO>> updateStatusHistory(
            @PathVariable UUID applicationId,
            @PathVariable UUID statusHistoryId,
            @Valid @RequestBody LoanApplicationStatusHistoryDTO dto) {
        return service.updateStatusHistory(applicationId, statusHistoryId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{statusHistoryId}")
    @Operation(summary = "Delete a status record", description = "Removes a status history entry.")
    public Mono<ResponseEntity<Void>> deleteStatusHistory(
            @PathVariable UUID applicationId,
            @PathVariable UUID statusHistoryId) {
        return service.deleteStatusHistory(applicationId, statusHistoryId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}