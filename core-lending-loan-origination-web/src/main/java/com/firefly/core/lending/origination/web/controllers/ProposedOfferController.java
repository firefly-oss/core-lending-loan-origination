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


package com.firefly.core.lending.origination.web.controllers.offer.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.ProposedOfferService;
import com.firefly.core.lending.origination.interfaces.dtos.ProposedOfferDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/offers")
@RequiredArgsConstructor
@Tag(name = "ProposedOffer", description = "Manage proposed loan offers for a loan application")
public class ProposedOfferController {

    private final ProposedOfferService service;

    @GetMapping
    @Operation(summary = "List offers", description = "Retrieves a paginated list of proposed offers for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ProposedOfferDTO>>> findAllOffers(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new offer", description = "Adds a new proposed offer to a loan application.")
    public Mono<ResponseEntity<ProposedOfferDTO>> createOffer(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ProposedOfferDTO dto) {
        return service.createOffer(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{offerId}")
    @Operation(summary = "Get an offer", description = "Fetch a specific offer record by ID.")
    public Mono<ResponseEntity<ProposedOfferDTO>> getOffer(
            @PathVariable UUID applicationId,
            @PathVariable UUID offerId) {
        return service.getOffer(applicationId, offerId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{offerId}")
    @Operation(summary = "Update an offer", description = "Updates the details of an existing proposed offer.")
    public Mono<ResponseEntity<ProposedOfferDTO>> updateOffer(
            @PathVariable UUID applicationId,
            @PathVariable UUID offerId,
            @Valid @RequestBody ProposedOfferDTO dto) {
        return service.updateOffer(applicationId, offerId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{offerId}")
    @Operation(summary = "Delete an offer", description = "Removes a proposed offer from the application.")
    public Mono<ResponseEntity<Void>> deleteOffer(
            @PathVariable UUID applicationId,
            @PathVariable UUID offerId) {
        return service.deleteOffer(applicationId, offerId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
