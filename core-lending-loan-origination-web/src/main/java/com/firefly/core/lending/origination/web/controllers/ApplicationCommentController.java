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


package com.firefly.core.lending.origination.web.controllers.comment.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.ApplicationCommentService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationCommentDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/comments")
@RequiredArgsConstructor
@Tag(name = "ApplicationComment", description = "Manage comments for a loan application")
public class ApplicationCommentController {

    private final ApplicationCommentService service;

    @GetMapping
    @Operation(summary = "List comments", description = "Retrieves a paginated list of comments for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationCommentDTO>>> findAllComments(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a comment", description = "Adds a new comment record for the application.")
    public Mono<ResponseEntity<ApplicationCommentDTO>> createComment(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationCommentDTO dto) {
        return service.createComment(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Get a comment", description = "Fetch a specific comment record by ID.")
    public Mono<ResponseEntity<ApplicationCommentDTO>> getComment(
            @PathVariable UUID applicationId,
            @PathVariable UUID commentId) {
        return service.getComment(applicationId, commentId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Update a comment", description = "Updates an existing comment record.")
    public Mono<ResponseEntity<ApplicationCommentDTO>> updateComment(
            @PathVariable UUID applicationId,
            @PathVariable UUID commentId,
            @Valid @RequestBody ApplicationCommentDTO dto) {
        return service.updateComment(applicationId, commentId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete a comment", description = "Removes a comment record from the application.")
    public Mono<ResponseEntity<Void>> deleteComment(
            @PathVariable UUID applicationId,
            @PathVariable UUID commentId) {
        return service.deleteComment(applicationId, commentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

