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


package com.firefly.core.lending.origination.web.controllers.fee.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.ApplicationFeeService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationFeeDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/fees")
@RequiredArgsConstructor
@Tag(name = "ApplicationFee", description = "Manage fees for a loan application")
public class ApplicationFeeController {

    private final ApplicationFeeService service;

    @GetMapping
    @Operation(summary = "List fees", description = "Retrieves a paginated list of fees for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationFeeDTO>>> findAllFees(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a fee", description = "Adds a new fee record for the application.")
    public Mono<ResponseEntity<ApplicationFeeDTO>> createFee(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationFeeDTO dto) {
        return service.createFee(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{feeId}")
    @Operation(summary = "Get a fee", description = "Fetch a specific fee record by ID.")
    public Mono<ResponseEntity<ApplicationFeeDTO>> getFee(
            @PathVariable UUID applicationId,
            @PathVariable UUID feeId) {
        return service.getFee(applicationId, feeId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{feeId}")
    @Operation(summary = "Update a fee", description = "Updates an existing fee record.")
    public Mono<ResponseEntity<ApplicationFeeDTO>> updateFee(
            @PathVariable UUID applicationId,
            @PathVariable UUID feeId,
            @Valid @RequestBody ApplicationFeeDTO dto) {
        return service.updateFee(applicationId, feeId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{feeId}")
    @Operation(summary = "Delete a fee", description = "Removes a fee record from the application.")
    public Mono<ResponseEntity<Void>> deleteFee(
            @PathVariable UUID applicationId,
            @PathVariable UUID feeId) {
        return service.deleteFee(applicationId, feeId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

