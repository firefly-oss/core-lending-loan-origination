package com.firefly.core.lending.origination.web.controllers.collateral.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.collateral.v1.ApplicationCollateralService;
import com.firefly.core.lending.origination.interfaces.dtos.collateral.v1.ApplicationCollateralDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/loan-applications/{applicationId}/collaterals")
@RequiredArgsConstructor
@Tag(name = "ApplicationCollateral", description = "Manage collateral items for a loan application")
public class ApplicationCollateralController {

    private final ApplicationCollateralService service;

    @GetMapping
    @Operation(summary = "List collaterals", description = "Retrieves a paginated list of collateral items for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<ApplicationCollateralDTO>>> findAllCollaterals(
            @PathVariable Long applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add a collateral item", description = "Adds a new collateral record to the application.")
    public Mono<ResponseEntity<ApplicationCollateralDTO>> createCollateral(
            @PathVariable Long applicationId,
            @RequestBody ApplicationCollateralDTO dto) {
        return service.createCollateral(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{collateralId}")
    @Operation(summary = "Get a collateral item", description = "Fetch a specific collateral record by ID.")
    public Mono<ResponseEntity<ApplicationCollateralDTO>> getCollateral(
            @PathVariable Long applicationId,
            @PathVariable Long collateralId) {
        return service.getCollateral(applicationId, collateralId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{collateralId}")
    @Operation(summary = "Update a collateral item", description = "Updates the details of an existing collateral record.")
    public Mono<ResponseEntity<ApplicationCollateralDTO>> updateCollateral(
            @PathVariable Long applicationId,
            @PathVariable Long collateralId,
            @RequestBody ApplicationCollateralDTO dto) {
        return service.updateCollateral(applicationId, collateralId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{collateralId}")
    @Operation(summary = "Delete a collateral item", description = "Removes a collateral record from the application.")
    public Mono<ResponseEntity<Void>> deleteCollateral(
            @PathVariable Long applicationId,
            @PathVariable Long collateralId) {
        return service.deleteCollateral(applicationId, collateralId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}