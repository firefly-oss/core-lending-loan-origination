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


package com.firefly.core.lending.origination.web.controllers.catalog.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.catalog.v1.ApplicationStatusService;
import com.firefly.core.lending.origination.interfaces.dtos.catalog.v1.ApplicationStatusDTO;
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
@RequestMapping("/api/v1/application-statuses")
@RequiredArgsConstructor
@Tag(name = "ApplicationStatus", description = "Operations on Application Statuses")
public class ApplicationStatusController {

    private final ApplicationStatusService applicationStatusService;

    @GetMapping
    @Operation(summary = "Filter application statuses", description = "Returns a paginated list of application statuses using filtering.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationStatusDTO>>> filterApplicationStatuses(
            @ParameterObject @ModelAttribute FilterRequest<ApplicationStatusDTO> filterRequest
    ) {
        return applicationStatusService.filterApplicationStatuses(filterRequest).map(ResponseEntity::ok);
    }

    @GetMapping("/all")
    @Operation(summary = "List all application statuses", description = "Returns a paginated list of application statuses without specific filters.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationStatusDTO>>> listApplicationStatuses(
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return applicationStatusService.listApplicationStatuses(paginationRequest).map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new application status", description = "Creates a new application status record.")
    public Mono<ResponseEntity<ApplicationStatusDTO>> createApplicationStatus(@Valid @RequestBody ApplicationStatusDTO dto) {
        return applicationStatusService.createApplicationStatus(dto).map(ResponseEntity::ok);
    }

    @GetMapping("/{applicationStatusId}")
    @Operation(summary = "Get application status by ID", description = "Retrieves a specific application status.")
    public Mono<ResponseEntity<ApplicationStatusDTO>> getApplicationStatus(@PathVariable UUID applicationStatusId) {
        return applicationStatusService.getApplicationStatus(applicationStatusId).map(ResponseEntity::ok);
    }

    @PutMapping("/{applicationStatusId}")
    @Operation(summary = "Update application status", description = "Updates an existing application status record.")
    public Mono<ResponseEntity<ApplicationStatusDTO>> updateApplicationStatus(
            @PathVariable UUID applicationStatusId,
            @Valid @RequestBody ApplicationStatusDTO dto) {
        return applicationStatusService.updateApplicationStatus(applicationStatusId, dto).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{applicationStatusId}")
    @Operation(summary = "Delete application status", description = "Deletes an application status by ID.")
    public Mono<ResponseEntity<Void>> deleteApplicationStatus(@PathVariable UUID applicationStatusId) {
        return applicationStatusService.deleteApplicationStatus(applicationStatusId).map(ResponseEntity::ok);
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get application status by code", description = "Retrieves a specific application status by its code.")
    public Mono<ResponseEntity<ApplicationStatusDTO>> getApplicationStatusByCode(@PathVariable String code) {
        return applicationStatusService.findByCode(code).map(ResponseEntity::ok);
    }
}