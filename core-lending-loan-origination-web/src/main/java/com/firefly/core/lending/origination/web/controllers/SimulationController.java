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

import com.firefly.core.lending.origination.core.services.SimulationService;
import com.firefly.core.lending.origination.interfaces.dtos.SimulationDTO;
import com.firefly.core.lending.origination.interfaces.dtos.UpdateSimulationCalculationsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST endpoints for persisted lending simulations.
 *
 * <p>Calculation is performed by {@code domain-core-pricing-engine}; this controller
 * only exposes persistence operations:</p>
 * <ul>
 *     <li>{@code POST /} — create the simulation row (calculation values may be empty).</li>
 *     <li>{@code GET /{id}} — fetch a simulation.</li>
 *     <li>{@code PATCH /{id}} — write back the computed financial values.</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/simulations")
@RequiredArgsConstructor
@Tag(name = "Simulation", description = "Persisted lending simulations (PERSONAL_LOAN, LEASING)")
public class SimulationController {

    private final SimulationService service;

    @PostMapping
    @Operation(
            operationId = "createSimulation",
            summary = "Create simulation",
            description = "Persists a new lending simulation. Calculation values may be supplied by the pricing engine or left blank.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Simulation persisted",
                    content = @Content(schema = @Schema(implementation = SimulationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid simulation payload")
    })
    public Mono<ResponseEntity<SimulationDTO>> createSimulation(@Valid @RequestBody SimulationDTO body) {
        return service.create(body)
                .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved));
    }

    @GetMapping("/{id}")
    @Operation(
            operationId = "getSimulation",
            summary = "Get simulation",
            description = "Retrieves a persisted simulation by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Simulation found",
                    content = @Content(schema = @Schema(implementation = SimulationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Simulation not found")
    })
    public Mono<ResponseEntity<SimulationDTO>> getSimulation(@PathVariable("id") UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @Operation(
            operationId = "updateSimulation",
            summary = "Update simulation calculations",
            description = "Writes the computed monthly payment, TIN, TAE and total amount onto an existing simulation row.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Simulation updated",
                    content = @Content(schema = @Schema(implementation = SimulationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid update payload"),
            @ApiResponse(responseCode = "404", description = "Simulation not found")
    })
    public Mono<ResponseEntity<SimulationDTO>> updateSimulation(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateSimulationCalculationsRequest body) {
        return service.updateCalculations(
                        id,
                        body.getMonthlyPayment(),
                        body.getTin(),
                        body.getTae(),
                        body.getTotalAmount())
                .map(ResponseEntity::ok);
    }
}
