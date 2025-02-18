package com.catalis.core.lending.origination.web.controllers.party.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.origination.core.services.party.v1.ApplicationPartyService;
import com.catalis.core.lending.origination.interfaces.dtos.party.v1.ApplicationPartyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/loan-applications/{applicationId}/parties")
@RequiredArgsConstructor
@Tag(name = "ApplicationParty", description = "Manage parties for a loan application")
public class ApplicationPartyController {

    private final ApplicationPartyService service;

    @GetMapping
    @Operation(summary = "List parties", description = "Retrieves a paginated list of parties tied to a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationPartyDTO>>> findAllParties(
            @PathVariable Long applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a new party", description = "Adds a new party (co-applicant, guarantor, etc.) to the application.")
    public Mono<ResponseEntity<ApplicationPartyDTO>> createParty(
            @PathVariable Long applicationId,
            @RequestBody ApplicationPartyDTO dto) {
        return service.createParty(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{partyId}")
    @Operation(summary = "Get a party", description = "Fetches details of a specific party associated with the application.")
    public Mono<ResponseEntity<ApplicationPartyDTO>> getParty(
            @PathVariable Long applicationId,
            @PathVariable Long partyId) {
        return service.getParty(applicationId, partyId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{partyId}")
    @Operation(summary = "Update a party", description = "Updates a party's information.")
    public Mono<ResponseEntity<ApplicationPartyDTO>> updateParty(
            @PathVariable Long applicationId,
            @PathVariable Long partyId,
            @RequestBody ApplicationPartyDTO dto) {
        return service.updateParty(applicationId, partyId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{partyId}")
    @Operation(summary = "Remove a party", description = "Deletes the association of a party from the application.")
    public Mono<ResponseEntity<Void>> deleteParty(
            @PathVariable Long applicationId,
            @PathVariable Long partyId) {
        return service.deleteParty(applicationId, partyId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}