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


package com.firefly.core.lending.origination.web.controllers.collateral.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.ApplicationCollateralService;
import com.firefly.core.lending.origination.interfaces.dtos.ApplicationCollateralDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/collaterals")
@RequiredArgsConstructor
@Tag(name = "ApplicationCollateral", description = "Manage collateral items for a loan application")
public class ApplicationCollateralController {

    private final ApplicationCollateralService service;

    @GetMapping
    @Operation(summary = "List collaterals", description = "Retrieves a paginated list of collateral items for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationCollateralDTO>>> findAllCollaterals(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a collateral item", description = "Adds a new collateral record to the application.")
    public Mono<ResponseEntity<ApplicationCollateralDTO>> createCollateral(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationCollateralDTO dto) {
        return service.createCollateral(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{collateralId}")
    @Operation(summary = "Get a collateral item", description = "Fetch a specific collateral record by ID.")
    public Mono<ResponseEntity<ApplicationCollateralDTO>> getCollateral(
            @PathVariable UUID applicationId,
            @PathVariable UUID collateralId) {
        return service.getCollateral(applicationId, collateralId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{collateralId}")
    @Operation(summary = "Update a collateral item", description = "Updates the details of an existing collateral record.")
    public Mono<ResponseEntity<ApplicationCollateralDTO>> updateCollateral(
            @PathVariable UUID applicationId,
            @PathVariable UUID collateralId,
            @Valid @RequestBody ApplicationCollateralDTO dto) {
        return service.updateCollateral(applicationId, collateralId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{collateralId}")
    @Operation(summary = "Delete a collateral item", description = "Removes a collateral record from the application.")
    public Mono<ResponseEntity<Void>> deleteCollateral(
            @PathVariable UUID applicationId,
            @PathVariable UUID collateralId) {
        return service.deleteCollateral(applicationId, collateralId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}