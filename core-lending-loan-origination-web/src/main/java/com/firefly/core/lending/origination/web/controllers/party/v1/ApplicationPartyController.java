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


package com.firefly.core.lending.origination.web.controllers.party.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.party.v1.ApplicationPartyService;
import com.firefly.core.lending.origination.interfaces.dtos.party.v1.ApplicationPartyDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/parties")
@RequiredArgsConstructor
@Tag(name = "ApplicationParty", description = "Manage parties for a loan application")
public class ApplicationPartyController {

    private final ApplicationPartyService service;

    @GetMapping
    @Operation(summary = "List parties", description = "Retrieves a paginated list of parties tied to a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationPartyDTO>>> findAllParties(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a new party", description = "Adds a new party (co-applicant, guarantor, etc.) to the application.")
    public Mono<ResponseEntity<ApplicationPartyDTO>> createParty(
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationPartyDTO dto) {
        return service.createParty(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{partyId}")
    @Operation(summary = "Get a party", description = "Fetches details of a specific party associated with the application.")
    public Mono<ResponseEntity<ApplicationPartyDTO>> getParty(
            @PathVariable UUID applicationId,
            @PathVariable UUID partyId) {
        return service.getParty(applicationId, partyId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{partyId}")
    @Operation(summary = "Update a party", description = "Updates a party's information.")
    public Mono<ResponseEntity<ApplicationPartyDTO>> updateParty(
            @PathVariable UUID applicationId,
            @PathVariable UUID partyId,
            @Valid @RequestBody ApplicationPartyDTO dto) {
        return service.updateParty(applicationId, partyId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{partyId}")
    @Operation(summary = "Remove a party", description = "Deletes the association of a party from the application.")
    public Mono<ResponseEntity<Void>> deleteParty(
            @PathVariable UUID applicationId,
            @PathVariable UUID partyId) {
        return service.deleteParty(applicationId, partyId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}