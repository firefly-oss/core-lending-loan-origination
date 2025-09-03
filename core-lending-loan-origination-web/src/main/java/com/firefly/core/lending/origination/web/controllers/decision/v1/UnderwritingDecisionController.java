package com.firefly.core.lending.origination.web.controllers.decision.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.origination.core.services.decision.v1.UnderwritingDecisionService;
import com.firefly.core.lending.origination.interfaces.dtos.decision.v1.UnderwritingDecisionDTO;
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
@RequestMapping("/api/v1/loan-applications/{applicationId}/decisions")
@RequiredArgsConstructor
@Tag(name = "UnderwritingDecision", description = "Manage underwriting decisions for a loan application")
public class UnderwritingDecisionController {

    private final UnderwritingDecisionService service;

    @GetMapping
    @Operation(summary = "List decisions", description = "Retrieves a paginated list of underwriting decisions for a loan application.")
    public Mono<ResponseEntity<PaginationResponse<UnderwritingDecisionDTO>>> findAllDecisions(
            @PathVariable UUID applicationId,
            @ParameterObject @ModelAttribute PaginationRequest paginationRequest) {
        return service.findAll(applicationId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new decision", description = "Records a new underwriting decision (APPROVED, REJECTED, etc.).")
    public Mono<ResponseEntity<UnderwritingDecisionDTO>> createDecision(
            @PathVariable UUID applicationId,
            @Valid @RequestBody UnderwritingDecisionDTO dto) {
        return service.createDecision(applicationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{decisionId}")
    @Operation(summary = "Get a decision", description = "Fetch a specific underwriting decision record by ID.")
    public Mono<ResponseEntity<UnderwritingDecisionDTO>> getDecision(
            @PathVariable UUID applicationId,
            @PathVariable UUID decisionId) {
        return service.getDecision(applicationId, decisionId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{decisionId}")
    @Operation(summary = "Update a decision", description = "Updates an existing underwriting decision record.")
    public Mono<ResponseEntity<UnderwritingDecisionDTO>> updateDecision(
            @PathVariable UUID applicationId,
            @PathVariable UUID decisionId,
            @Valid @RequestBody UnderwritingDecisionDTO dto) {
        return service.updateDecision(applicationId, decisionId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{decisionId}")
    @Operation(summary = "Delete a decision", description = "Removes a decision record from the application.")
    public Mono<ResponseEntity<Void>> deleteDecision(
            @PathVariable UUID applicationId,
            @PathVariable UUID decisionId) {
        return service.deleteDecision(applicationId, decisionId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}