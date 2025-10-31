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


package com.firefly.core.lending.origination.web.controllers.condition.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.condition.v1.ApplicationConditionService;
import com.firefly.core.lending.origination.interfaces.dtos.condition.v1.ApplicationConditionDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/conditions")
@RequiredArgsConstructor
@Tag(name = "ApplicationCondition", description = "Manage conditions for a loan application")
public class ApplicationConditionController {

    private final ApplicationConditionService service;

    @GetMapping
    @Operation(summary = "List conditions", description = "Retrieves a paginated list of conditions for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationConditionDTO>>> findAllConditions(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a condition", description = "Adds a new condition record for the application.")
    public Mono<ResponseEntity<ApplicationConditionDTO>> createCondition(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationConditionDTO dto) {
        return service.createCondition(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{conditionId}")
    @Operation(summary = "Get a condition", description = "Fetch a specific condition record by ID.")
    public Mono<ResponseEntity<ApplicationConditionDTO>> getCondition(
            @PathVariable UUID applicationId,
            @PathVariable UUID conditionId) {
        return service.getCondition(applicationId, conditionId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{conditionId}")
    @Operation(summary = "Update a condition", description = "Updates an existing condition record.")
    public Mono<ResponseEntity<ApplicationConditionDTO>> updateCondition(
            @PathVariable UUID applicationId,
            @PathVariable UUID conditionId,
            @Valid @RequestBody ApplicationConditionDTO dto) {
        return service.updateCondition(applicationId, conditionId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{conditionId}")
    @Operation(summary = "Delete a condition", description = "Removes a condition record from the application.")
    public Mono<ResponseEntity<Void>> deleteCondition(
            @PathVariable UUID applicationId,
            @PathVariable UUID conditionId) {
        return service.deleteCondition(applicationId, conditionId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

