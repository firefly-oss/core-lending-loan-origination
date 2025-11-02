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
import com.firefly.core.lending.origination.core.services.ApplicationTaskService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationTaskDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/tasks")
@RequiredArgsConstructor
@Tag(name = "ApplicationTask", description = "Manage tasks for a loan application")
public class ApplicationTaskController {

    private final ApplicationTaskService service;

    @GetMapping
    @Operation(summary = "List tasks", description = "Retrieves a paginated list of tasks for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationTaskDTO>>> findAllTasks(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a task", description = "Adds a new task record for the application.")
    public Mono<ResponseEntity<ApplicationTaskDTO>> createTask(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationTaskDTO dto) {
        return service.createTask(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get a task", description = "Fetch a specific task record by ID.")
    public Mono<ResponseEntity<ApplicationTaskDTO>> getTask(
            @PathVariable UUID applicationId,
            @PathVariable UUID taskId) {
        return service.getTask(applicationId, taskId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Update a task", description = "Updates an existing task record.")
    public Mono<ResponseEntity<ApplicationTaskDTO>> updateTask(
            @PathVariable UUID applicationId,
            @PathVariable UUID taskId,
            @Valid @RequestBody ApplicationTaskDTO dto) {
        return service.updateTask(applicationId, taskId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete a task", description = "Removes a task record from the application.")
    public Mono<ResponseEntity<Void>> deleteTask(
            @PathVariable UUID applicationId,
            @PathVariable UUID taskId) {
        return service.deleteTask(applicationId, taskId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

