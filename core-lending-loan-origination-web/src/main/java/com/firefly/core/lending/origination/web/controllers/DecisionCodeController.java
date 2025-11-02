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

import com.firefly.core.lending.origination.core.services.DecisionCodeService;
import com.firefly.core.lending.origination.models.entities.DecisionCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/decision-codes")
@RequiredArgsConstructor
@Tag(name = "DecisionCode", description = "Manage decision codes for underwriting decisions")
public class DecisionCodeController {

    private final DecisionCodeService decisionCodeService;

    @GetMapping
    @Operation(summary = "List all decision codes", description = "Retrieves all available decision codes.")
    public Flux<DecisionCode> getAllDecisionCodes() {
        return decisionCodeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get decision code by ID", description = "Retrieves a specific decision code by its ID.")
    public Mono<DecisionCode> getDecisionCodeById(@PathVariable UUID id) {
        return decisionCodeService.findById(id);
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get decision code by code", description = "Retrieves a specific decision code by its code.")
    public Mono<DecisionCode> getDecisionCodeByCode(@PathVariable String code) {
        return decisionCodeService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create decision code", description = "Creates a new decision code.")
    public Mono<DecisionCode> createDecisionCode(@Valid @RequestBody DecisionCode decisionCode) {
        return decisionCodeService.save(decisionCode);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update decision code", description = "Updates an existing decision code.")
    public Mono<DecisionCode> updateDecisionCode(@PathVariable UUID id, @Valid @RequestBody DecisionCode decisionCode) {
        decisionCode.setDecisionCodeId(id);
        return decisionCodeService.save(decisionCode);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete decision code", description = "Deletes a decision code.")
    public Mono<Void> deleteDecisionCode(@PathVariable UUID id) {
        return decisionCodeService.deleteById(id);
    }
}