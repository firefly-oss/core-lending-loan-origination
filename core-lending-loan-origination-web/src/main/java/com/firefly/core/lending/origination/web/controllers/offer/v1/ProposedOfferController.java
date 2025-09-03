package com.firefly.core.lending.origination.web.controllers.offer.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.offer.v1.ProposedOfferService;
import com.firefly.core.lending.origination.interfaces.dtos.offer.v1.ProposedOfferDTO;
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
@RequestMapping("/loan-applications/{applicationId}/offers")
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
