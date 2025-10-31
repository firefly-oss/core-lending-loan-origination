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


package com.firefly.core.lending.origination.web.controllers.metric.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.metric.v1.ApplicationMetricService;
import com.firefly.core.lending.origination.interfaces.dtos.metric.v1.ApplicationMetricDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/metrics")
@RequiredArgsConstructor
@Tag(name = "ApplicationMetric", description = "Manage metrics for a loan application")
public class ApplicationMetricController {

    private final ApplicationMetricService service;

    @GetMapping
    @Operation(summary = "List metrics", description = "Retrieves a paginated list of metrics for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationMetricDTO>>> findAllMetrics(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a metric", description = "Adds a new metric record for the application.")
    public Mono<ResponseEntity<ApplicationMetricDTO>> createMetric(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationMetricDTO dto) {
        return service.createMetric(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{metricId}")
    @Operation(summary = "Get a metric", description = "Fetch a specific metric record by ID.")
    public Mono<ResponseEntity<ApplicationMetricDTO>> getMetric(
            @PathVariable UUID applicationId,
            @PathVariable UUID metricId) {
        return service.getMetric(applicationId, metricId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{metricId}")
    @Operation(summary = "Update a metric", description = "Updates an existing metric record.")
    public Mono<ResponseEntity<ApplicationMetricDTO>> updateMetric(
            @PathVariable UUID applicationId,
            @PathVariable UUID metricId,
            @Valid @RequestBody ApplicationMetricDTO dto) {
        return service.updateMetric(applicationId, metricId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{metricId}")
    @Operation(summary = "Delete a metric", description = "Removes a metric record from the application.")
    public Mono<ResponseEntity<Void>> deleteMetric(
            @PathVariable UUID applicationId,
            @PathVariable UUID metricId) {
        return service.deleteMetric(applicationId, metricId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

