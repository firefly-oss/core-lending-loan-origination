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


package com.firefly.core.lending.origination.web.controllers.score.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.UnderwritingScoreService;
import com.firefly.core.lending.origination.interfaces.dtos.UnderwritingScoreDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/scores")
@RequiredArgsConstructor
@Tag(name = "UnderwritingScore", description = "Manage underwriting score records for a loan application")
public class UnderwritingScoreController {

    private final UnderwritingScoreService service;

    @GetMapping
    @Operation(summary = "List scores", description = "Retrieves a paginated list of underwriting scores for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<UnderwritingScoreDTO>>> findAllScores(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new score", description = "Adds a new underwriting score record.")
    public Mono<ResponseEntity<UnderwritingScoreDTO>> createScore(
            @PathVariable UUID applicationId,
            @Valid @RequestBody UnderwritingScoreDTO dto) {
        return service.createScore(applicationId, dto)
                .map(score -> ResponseEntity.status(201).body(score));
    }

    @GetMapping("/{scoreId}")
    @Operation(summary = "Get a score", description = "Fetch a specific underwriting score record by ID.")
    public Mono<ResponseEntity<UnderwritingScoreDTO>> getScore(
            @PathVariable UUID applicationId,
            @PathVariable UUID scoreId) {
        return service.getScore(applicationId, scoreId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{scoreId}")
    @Operation(summary = "Update a score", description = "Updates an existing underwriting score record.")
    public Mono<ResponseEntity<UnderwritingScoreDTO>> updateScore(
            @PathVariable UUID applicationId,
            @PathVariable UUID scoreId,
            @Valid @RequestBody UnderwritingScoreDTO dto) {
        return service.updateScore(applicationId, scoreId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{scoreId}")
    @Operation(summary = "Delete a score", description = "Removes a scoring record from the application.")
    public Mono<ResponseEntity<Void>> deleteScore(
            @PathVariable UUID applicationId,
            @PathVariable UUID scoreId) {
        return service.deleteScore(applicationId, scoreId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}