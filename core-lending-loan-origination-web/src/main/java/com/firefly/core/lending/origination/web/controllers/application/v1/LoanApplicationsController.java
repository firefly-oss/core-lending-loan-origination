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


package com.firefly.core.lending.origination.web.controllers.application.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.application.v1.LoanApplicationsService;
import com.firefly.core.lending.origination.interfaces.dtos.application.v1.LoanApplicationDTO;
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
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
@Tag(name = "LoanApplications", description = "Operations on Loan Applications")
public class LoanApplicationsController {

    private final LoanApplicationsService service;

    @GetMapping
    @Operation(summary = "Filter loan applications", description = "Returns a paginated list of loan applications using filtering.")
    public Mono<ResponseEntity<PaginationResponse<LoanApplicationDTO>>> filterLoanApplications(
            @ParameterObject @ModelAttribute FilterRequest<LoanApplicationDTO> filterRequest
    ) {
        return service.filterLoanApplications(filterRequest).map(ResponseEntity::ok);
    }

    @GetMapping("/all")
    @Operation(summary = "List all loan applications", description = "Returns a paginated list of loan applications without specific filters.")
    public Mono<ResponseEntity<PaginationResponse<LoanApplicationDTO>>> listLoanApplications(
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.listLoanApplications(paginationRequest).map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new loan application", description = "Creates a new loan application record.")
    public Mono<ResponseEntity<LoanApplicationDTO>> createLoanApplication(@Valid @RequestBody LoanApplicationDTO dto) {
        return service.createLoanApplication(dto).map(ResponseEntity::ok);
    }

    @GetMapping("/{applicationId}")
    @Operation(summary = "Get loan application by ID", description = "Retrieves a specific loan application.")
    public Mono<ResponseEntity<LoanApplicationDTO>> getLoanApplication(@PathVariable UUID applicationId) {
        return service.getLoanApplication(applicationId).map(ResponseEntity::ok);
    }

    @PutMapping("/{applicationId}")
    @Operation(summary = "Update loan application", description = "Updates an existing loan application record.")
    public Mono<ResponseEntity<LoanApplicationDTO>> updateLoanApplication(
            @PathVariable UUID applicationId,
            @Valid @RequestBody LoanApplicationDTO dto) {
        return service.updateLoanApplication(applicationId, dto).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{applicationId}")
    @Operation(summary = "Delete loan application", description = "Deletes a loan application by ID.")
    public Mono<ResponseEntity<Void>> deleteLoanApplication(@PathVariable UUID applicationId) {
        return service.deleteLoanApplication(applicationId).map(ResponseEntity::ok);
    }
}